$(function() {
//Main functions:
    loadAllBookings();
});


//** Manager View **

//ajax call to the api to get the JSON data - if successful it uses data to draw a list of bookings if not it returns an error
function loadAllBookings() {
    var bookingsByCity = "/api/manager_view/bcn";
    var table = "#managerViewTableBcn";
//TODO: need a variable to change bcn to svq for sevilla
 $.getJSON(bookingsByCity) //TODO : need to get this to get a different urls for bcn and svq
 .done(function(data) {
     bookingsMap(data, table);
 })
 .fail(function( jqXHR, textStatus ) {
     $(table).text("Failed: " + textStatus);
 });
}


//get data from JSON and list of all games with game id, creation date, players emails
function bookingsMap(data, table) {
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
     //TODO: pass tabel id through variable 'table' if it should load in bcn or svq table
     $(table).append("<tr>" + "<td>" + booking.number + "</td>" +
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
