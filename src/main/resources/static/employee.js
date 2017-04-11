$(function() {
//Main functions:
    loadBookingsByEmployee();
});

//TODO: Need to reset tables each time
function loadBookingsByEmployee() {
//need to get employee id from url
    var employeeId = getEmployeeIdFromURL(); //gets the employee id number from the url
    var url = 'api/employee_view/' + employeeId
    $.get(url)
    .done(function(data) {
        employeeBookingsMap(data);
    })
    .fail(function( jqXHR, textStatus ) {
    });
}

//get data from JSON and list of all games with game id, creation date, players emails
function employeeBookingsMap(data) {
 data.map(function(bookingData) {
     $('.employee_view').empty(); //reset table contents
     var booking = {};
     booking.number = bookingData.bookingNumber;
     booking.guest = bookingData.guestName;
     booking.web = bookingData.reservationWebsite;
     booking.checkIn = bookingData.checkIn;
     booking.checkOut = bookingData.checkOut;
     booking.rating = bookingData.rating;
     booking.url = bookingData.reviewURL;
     $('.employee_view').append("<tr>" + "<td>" + booking.number + "</td>" +
                                   "<td>" + booking.guest + "</td>" +
                                   "<td>" + booking.web + "</td>" +
                                   "<td>" + booking.checkIn + "</td>" +
                                   "<td>" + booking.checkOut + "</td>" +
                                   "<td>" + booking.rating + "</td>" +
                                   "<td>" + booking.url + "</td>");
 });
}


// This gets the employee Id query value from the url. document.location.search gives the query e.g. "?gp=1"
// document.location.search.substr(1) returns the parameter & its value without the & e.g. "employee=1"
function getEmployeeIdFromURL(){
  var query = document.location.search.substr(1).split('=') //e.g. takes "gp=1" & splits into ["gp", "1"]
  var employeeId = query[1];
  return employeeId;
}

//TODO: add search options by booking number, guest name etc
//TODO: add filter by assigned employee
