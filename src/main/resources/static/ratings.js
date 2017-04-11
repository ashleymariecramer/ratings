$(function() {
//Main functions:
    activateUserAccountFunctions();
//    populateDropdownWithAllAccommodationNames();
    activateCreateBookingFunctions();
    populateDropdownWithAllEmployeeUsernames();
    deactivateDatesPriorToAndIncludingCheckIn();
});



// ***  Auxiliary functions  ***  //

//Groups together all the functions related to creating, accessing user accounts
  function activateUserAccountFunctions(){
    logIn();
    logOut();
    showCreateAccountForm();
    signUp();
  }

//Groups together all the functions related to creating bookings
  function activateCreateBookingFunctions(){
    showCreateBookingForm();
    createNewBooking();
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
            location.reload();//Refreshes page to update with logged in user
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

  //** Show New User account creation form **
  function showCreateAccountForm() {
    $("#create_account_button").click(function(evt) {
        $("#login_form").hide(); //hides login
        $("#logout_form").hide(); //hides logout button
        $("#signup_form").show(); //shows sign up
    });
  }

  //** Sign up  **
  function signUp() {
        $("#signup_button").click(function(evt) {
        evt.preventDefault(); //used with forms to prevent them getting submitted automatically - used with 'onsubmit="return false"' in html
        var form = evt.target.form; //this is needed later to gets the values from the form
        if (validateSignupForm() == true) {
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

   //** Validate form for signup **
  //check all fields are filled in and email has correct format
  function validateSignupForm() {  //TODO maybe break these down into individual functions would be cleaner
      var name = document.forms["signup_form"]["firstName"].value;
          if (name == "") {
              sweetAlert("First Name must be filled out");
              return false;
          }
      var surname = document.forms["signup_form"]["surname"].value;
          if (surname == "") {
              sweetAlert("Surname must be filled out");
              return false;
          }
      var email = document.forms["signup_form"]["email"].value;
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
      var username = document.forms["signup_form"]["username"].value;
          if (username == "") {
              sweetAlert("Username must be filled out");
              return false;
          }
      var password = document.forms["signup_form"]["password"].value;
          if (password == "") {
              sweetAlert("Password must be filled out");
              return false;
          }
      var accommodationName = document.forms["signup_form"]["accommodationName"].value;
         if (accommodationName == "null") {
              sweetAlert("Workplace must be filled out");
              return false;
         }
      var role = document.forms["signup_form"]["role"].value;
          if (role == "null") {
              sweetAlert("Role must be filled out");
              return false;
          }
      return true;
  }

  //** Show New User account creation form **
  function showCreateBookingForm() {
      $("#create_booking_button").click(function(evt) {
          $("#login_form").hide(); //hides login
          $("#logout_form").hide(); //hides logout button
          $("#signup_form").hide(); //hides sign up
          $("#create_booking_form").show(); //hide create booking form
          //TODO: either have create guest and booking as separte forms to be shown/hidden or redirect
          //TODO: to new page
          //location.assign(url);//Takes user to game view for new game
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
  //group together various validation functions
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

//function populateDropdownWithAllAccommodationNames() {
//  $.get("/api/all_accommodation")
//  .done(function(data) {
//      for (var i = 0; i < data.length; i++) {
//        var accommodationList = '<option value=' + data[i].id + ' >' + data[i].name + '</option>'
//        $("#accommodationList").append(accommodationList);
//        $("#workplaceList").append(accommodationList);
//        }
//  })
//  .fail(function( jqXHR, textStatus ) {
//  });
//}



function populateDropdownWithAllEmployeeUsernames() {
//need to trigger this once the accommodation name has been selected
    $('#accommodationList').change(function() {
        //reset drop down menu to avoid duplicating employees
        $("#employeeList").html('<option value="null"> Choose person </option>');
        var accomName = $('#accommodationList').val(); //gets the gamePlayer(gp) id number from the url
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
