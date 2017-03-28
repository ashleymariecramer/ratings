$(function() {
//Main functions:
    loadAllBookings();
});


//** Manager View **

//ajax call to the api to get the JSON data - if successful it uses data to draw a list of bookings if not it returns an error
function loadAllBookings() {
 $.getJSON("/api/manager_view")
 .done(function(data) {
     bookingsMap(data);
 })
 .fail(function( jqXHR, textStatus ) {
     $("#managerViewTable").text("Failed: " + textStatus);
 });
}


//get data from JSON and list of all games with game id, creation date, players emails
function bookingsMap(data) {
 data.map(function(bookingData) {
     var booking = {};
     booking.number = bookingData.bookingNumber;
     booking.guest = bookingData.guestName;
     booking.web = bookingData.reservationWebsite;
     booking.checkIn = bookingData.checkIn;
     booking.checkOut = bookingData.checkOut;
     booking.rating = bookingData.rating;
     booking.url = bookingData.reviewURL;
     booking.employee = bookingData.employee;
     $("#managerViewTable").append("<tr>" + "<td>" + booking.number + "</td>" +
                                   "<td>" + booking.guest + "</td>" +
                                   "<td>" + booking.web + "</td>" +
                                   "<td>" + booking.checkIn + "</td>" +
                                   "<td>" + booking.checkOut + "</td>" +
                                   "<td>" + booking.rating + "</td>" +
                                   "<td>" + booking.url + "</td>" +
                                   "<td>" + booking.employee + "</td>" +
                                   "<td>" + "<button>" + "Edit" + "</button>" +
                                            "<button>" + "Delete" + "</button>" + "</td>" + "</tr>");
 });
}


//TODO: add search options by booking number, guest name etc
//TODO: add filter by assigned employee
