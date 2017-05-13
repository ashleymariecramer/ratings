$(function() {
//Main functions:
    loadCurrentUser();
    activateUserAccountFunctions();
});


// ***  Auxiliary functions  ***  //

//Groups together all the functions related to accessing user accounts
  function activateUserAccountFunctions(){
    logIn();
    logOut();
  }

//** Show create New User account button **
      function showCreateUserButton() {
        $("#create_user_button").show();
//        activateCreateUserFunctions(); //TODO: this isn't the place to put this we should wait til the button is clicked
      }

//** Show Create New Booking button **
      function showCreateBookingButton() {
        $("#create_booking_button").show();
//        activateCreateBookingFunctions(); //TODO: this isn't the place to put this we should wait til the button is clicked
      }

//Groups together all the functions related to creation of new user accounts
  function activateCreateUserFunctions(){
    showCreateUserForm();
    createNewUser();
    validateCreateUserForm();
  }

//Groups together all the functions related to creating bookings
  function activateCreateBookingFunctions(){
    showCreateBookingForm();
    populateDropdownWithAllEmployeeUsernames();
    deactivateDatesPriorToAndIncludingCheckIn();
    createNewBooking();
    validateBookingForm();
  }

//ajax call to the api to get the JSON data - if successful it uses data to draw a list of games if not it returns an error
  function loadCurrentUser() {
    $.getJSON("/api/currentUser")
    .done(function(data) {
          console.log("getJSON: " + data );
          loggedInUserMap(data);
          })
    .fail(function( jqXHR, textStatus ) {
      showOutput( "Failed: " + textStatus );
    });
  }

 //get data from JSON and create a new variable which contains the logged in users details.
  function loggedInUserMap(data) {
        console.log("loggedInUserMap: " + data )
        if (data.user == "guest"){
            $("#login_form").show(); //show login
            $("#logout_form").hide(); //hides logout button
            $("#current_user").append("<h3 class='warning'>" + "Please login" + "</h3>");
        }
        else{
            $("#login_form").hide(); //hides login
            $("#logout_form").show(); //shows logout button
            $("#current_user").append("<h2>" + "Hi there " + "<b>" + data.loggedInUser.firstName + "</b>" + "</h2>");
            console.log("loggedInUser Role: " + data.loggedInUser.role);
            var role = data.loggedInUser.role
        }
  }


  //** Login **
  function logIn() {
     $("#login_button").click(function(evt) {
        evt.preventDefault(); //used with forms to prevent them getting submitted automatically - used with 'onsubmit="return false"' in html
        var form = evt.target.form; //this is needed later to gets the values from the form
        $.post("/api/login",
               { username: form["username"].value,
                 password: form["password"].value })
         .done(function() {
            console.log("logged in!"); //to check login has worked
            showOptionsBasedOnUserRole("user");
            //location.reload();//Refreshes page to update with logged in user TODO: here redirect to page based on login user role
            })
         .fail(function(jqXHR, textStatus, errorThrown) {
              sweetAlert('Booh!', 'Wrong credentials, try again!', 'error');
              })
      });
  }

  //** Logout **
  function logOut() {
    $("#logout_button").click(function(evt) {
    evt.preventDefault(); //used with forms to prevent them getting submitted automatically - used with 'onsubmit="return false"' in html
    var form = evt.target.form; //this is needed later to gets the values from the form
    $.post("/api/logout")
     .done(function() {
        console.log("logged out!"); //to check login has worked
        location.reload();//Refreshes page to update with logged in user
        })
        .fail(function() {
            sweetAlert('Oops!', 'Something went wrong with the logout. Please try again!', 'error');
        })
    });
  }

  //** Determine the options to show user based on their role **
  function showOptionsBasedOnUserRole(role){
      console.log("role from showOptionFunction " + role) //this gets triggered and role is correctly identified
      if (role == "user"){
        console.log("you're just a user!") //but for some reason the if statement does not get triggered only the else
        var employeeId = getLoggedInEmployeeIdNumber();
        //redirect url to url with employeeId
        var url = 'employee_view.html?employeeId=' + employeeId;
        location.assign(url);
        showUserDashboard(); //This is getting triggered so why not the alert
        }
      else if (role == "manager"){
        console.log("you're manager!");
        showManagerDashboard();
        }
      else if (role == "admin"){
        console.log("you're admin!");
        showAdminDashboard();
        }
      else if (role == "developer"){
        console.log("you're a developer!");
        showDeveloperDashboard();
        }
      else {
      console.log("No corresponding role found for this user. Please contact system admin")
      }
  }

function getLoggedInEmployeeIdNumber() {
    var url = 'api/currentUserId'
    $.get(url)
    .done(function(data) {
    console.log("data: " + data);
        return data;
    })
    .fail(function( jqXHR, textStatus ) {
    });
}

  function showUserDashboard(){
    sweetAlert("triggered show user dashboard"); //this is working
    showCreateBookingButton();
    //loadBookingsByEmployee TODO: function to show employees bookings
  }

  function showManagerDashboard(){
    showCreateBookingButton();
    //loadAllBookings TODO: function to show all bookings
  }

  function showAdminDashboard(){
    showCreateUserButton();
    showCreateBookingButton();
    //loadAllBookings TODO: function to show all bookings (same as manager)
  }

  function showDeveloperDashboard(){
    showCreateUserButton();
    showCreateBookingButton();
  }

  // if user need to show create booking button, and show own bookings button (or show all directly)
  //if manager need show create booking button and show all bookings button (or just show all directly with filter options)
  //if admin same as manager plus create new user button
  //}


  //** Show New User account creation form **
  function showCreateUserForm() {
    $("#create_user_button").click(function(evt) {
        $("#login_form").hide(); //hides login
        $("#logout_form").hide(); //hides logout button
        $("#create_user_form").show(); //shows sign up
        $("#create_booking_form").hide();
    });
  }

  //** Create New User **
  function createNewUser() {
        $("#create_user_button").click(function(evt) {
        evt.preventDefault(); //used with forms to prevent them getting submitted automatically - used with 'onsubmit="return false"' in html
        var form = evt.target.form; //this is needed later to gets the values from the form
        if (validateCreateUserForm() == true) {
                $.post("/api/employees",
                       { firstName: form["firstName"].value,
                         surname: form["surname"].value,
                         email: form["email"].value,
                         username: form["username"].value,
                         password: form["password"].value,
                         accommodationName: form["accommodationName"].value,
                         role: form["role"].value })
                 .done(function() {
                    console.log("new account created!"); //to check login has worked
                    location.reload();//Refreshes page to update with logged in user
                    })
                 .fail(function(jqXHR, textStatus, errorThrown) {
                      sweetAlert("Oops", "Seems there was a problem! Please try again", "error");
                      })
        }
        });
  }

  //** Validate form for creating new users **
  //check all fields are filled in and email has correct format
  function validateCreateUserForm() {  //TODO maybe break these down into individual functions would be cleaner
      var name = document.forms["create_user_form"]["firstName"].value;
          if (name == "") {
              sweetAlert("First Name must be filled out");
              return false;
          }
      var surname = document.forms["create_user_form"]["surname"].value;
          if (surname == "") {
              sweetAlert("Surname must be filled out");
              return false;
          }
      var email = document.forms["create_user_form"]["email"].value;
          if (email == "") {
              sweetAlert("Email must be filled out");
              return false;
          }
      var atpos = email.indexOf("@");
      var dotpos = email.lastIndexOf(".");
          if (atpos<1 || dotpos<atpos+2 || dotpos+2>=email.length) {
              sweetAlert("Not a valid e-mail address");
               return false;
          }
      var username = document.forms["create_user_form"]["username"].value;
          if (username == "") {
              sweetAlert("Username must be filled out");
              return false;
          }
      var password = document.forms["create_user_form"]["password"].value;
          if (password == "") {
              sweetAlert("Password must be filled out");
              return false;
          }
      var accommodationName = document.forms["create_user_form"]["accommodationName"].value;
         if (accommodationName == "null") {
              sweetAlert("Workplace must be filled out");
              return false;
         }
      var role = document.forms["create_user_form"]["role"].value;
          if (role == "null") {
              sweetAlert("Role must be filled out");
              return false;
          }
      return true;
  }



  //** Show New Booking creation form **
  function showCreateBookingForm() {
      $("#create_booking_button").click(function(evt) {
          $("#login_form").hide();
          $("#logout_form").hide();
          $("#create_user_form").hide();
          $("#create_booking_form").show(); //show create booking form
      });
  }

  //** Create New Booking  **
  function createNewBooking() {
        $("#save_booking_button").click(function(evt) {
        evt.preventDefault(); //used with forms to prevent them getting submitted automatically - used with 'onsubmit="return false"' in html
        var form = evt.target.form; //this is needed later to gets the values from the form
        if (validateBookingForm()  == true) {
                $.post("/api/bookings",
                       { bookingNumber: form["bookingNumber"].value,
                         guestFirstName: form["guestFirstName"].value,
                         guestSurname: form["guestSurname"].value,
                         accommodationName: form["accommodationName"].value,
                         reservationWebsite: form["reservationWebsite"].value,
                         checkInDate: form["checkInDate"].value,
                         checkOutDate: form["checkOutDate"].value,
                         rating: form["rating"].value,
                         reviewURL: form["reviewURL"].value,
                         employee: form["employee"].value  })
                 .done(function() {
                    console.log("new booking created!"); //to check login has worked
                    location.reload();//Refreshes page to update with logged in user
                    })
                 .fail(function(jqXHR, textStatus, errorThrown) {
                      sweetAlert("Oops", "Seems there was a problem! Please try again", "error");
                      })
        }
        });
  }

  //** Validate form for create new booking**
  //groups together various validation functions
  function validateBookingForm() {
    if (checkBookingFormFieldsNotEmpty() == false){
          return false;
    }
    if (isCheckInDateLessThanThreeDaysAgo() == false){
        return false;
    }
    if (isCheckOutDateAfterCheckInDate() == false){
        return false;
    }
    return true;
  }

  //check all fields are filled in and have correct format
  function checkBookingFormFieldsNotEmpty() {
      var bookingNumber = document.forms["create_booking_form"]["bookingNumber"].value;
          if (bookingNumber == "") {
              sweetAlert("Booking number must be filled out");
              return false;
          }
      var guestFirstName = document.forms["create_booking_form"]["guestFirstName"].value;
          if (guestFirstName == "") {
              sweetAlert("Guest's first name must be filled out");
              return false;
          }
      var guestSurname = document.forms["create_booking_form"]["guestSurname"].value;
          if (guestSurname == "") {
             sweetAlert("Guest's surname must be filled out");
             return false;
          }
      var accommodationName = document.forms["create_booking_form"]["accommodationName"].value;
          if (accommodationName == "null") {
                sweetAlert("Add accommodation for this booking");
                return false;
          }
      var reservationWebsite = document.forms["create_booking_form"]["reservationWebsite"].value;
          if (reservationWebsite == "") {
              sweetAlert("Reservation Website must be filled out");
              return false;
          }
      var checkInDate = document.forms["create_booking_form"]["checkInDate"].value;
          if (checkInDate == "") {
              sweetAlert("Check-in Date must be filled out");
              return false;
          }
      var checkOutDate = document.forms["create_booking_form"]["checkOutDate"].value;
          if (checkOutDate == "") {
              sweetAlert("Check-out Date must be filled out");
              return false;
          }
      var employee = document.forms["create_booking_form"]["employee"].value;
          if (employee == "null") {
              sweetAlert("Add an employee as the Person Responsible for this booking");
              return false;
          }

      return true;
  }

  //TODO: need to change this to ONLY show employees with the role 'user'- best to change it at the DTO level directly
function populateDropdownWithAllEmployeeUsernames() {
//need to trigger this once the accommodation name has been selected
    $('#accommodationList').change(function() {
        //reset drop down menu to avoid duplicating employees
        $("#employeeList").html('<option value="null"> Choose person </option>');
        var accomName = $('#accommodationList').val(); //gets the user id number from the url
        console.log(accomName);
        var url = "/api/all_employees_usernames/" + accomName //inserts the accommodation name into the api
        console.log(url);
        $.get(url)
        .done(function(data) {
            console.log("get url");
            console.log(data);

          for (var i = 0; i < data.length; i++) {
            $("#employeeList").append('<option value=' + data[i].username + ' >' +
                               data[i].fullName + '</option>');
            }
        })
        .fail(function( jqXHR, textStatus ) {
        });
    });
}




function deactivateDatesPriorToAndIncludingCheckIn(){
//need to trigger this once the checkInDate has been selected
    $('#checkInDate').change(function() {
        var dateIn = $('#checkInDate').val();
        var earliestPossCheckOut = moment(dateIn).add(1, 'days').format('YYYY-MM-DD');
        $('#checkOutDate').attr('min', earliestPossCheckOut);
    });
}

function isCheckInDateLessThanThreeDaysAgo(){
    var dateIn = $('#checkInDate').val();
    var today = moment().format('YYYY-MM-DD'); //Gets today's date in the same format as datepicker
    var threeDaysAgo = moment(today).subtract(3, 'days').format('YYYY-MM-DD');
    if (moment(dateIn).isBefore(threeDaysAgo)){
        sweetAlert("The Check In Date you have entered is more than 3 days old");
        return false;
        }
    return true;
}

function isCheckOutDateAfterCheckInDate(){
    var dateIn = $('#checkInDate').val();
    var dateOut = $('#checkOutDate').val();
    if (moment(dateOut).isAfter(dateIn)){
        return true;
    }
    sweetAlert("Check Out Date must be after Check In Date!")
    return false;
}





//TODO: once login activated include field to show who created booking(logged in user at time) - and later for edits too.
