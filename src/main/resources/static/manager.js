$(function() {
//Main functions:
    hideAllTables();
    populateTableWithBookings();
//    loadAllBookings();
});


//** Manager View **
function hideAllTables(){
    $('.manager_view_all').hide();
    $('.manager_view_blackSwanBCN').hide();
    $('.manager_view_blackSwanSVQ').hide();
}



//TODO: Need to reset tables each time
function populateTableWithBookings() {
    $('#filter_buttons_for_bookings').children().click(function(){
    var accomName= $(this).val(); //gets value from clicked button
    var url =  'api/manager_view';
    //need to create a variable table so we know where to add in the data - using accomName
    var table = ".manager_view_" + accomName;
    var tableBody = "#manager_view_" + accomName;
    $(table).toggle(); //TODO: add a toggle to show/hide tables as buttons are clicked
    if (accomName != "all"){
        url = 'api/manager_view/' + accomName  //inserts the accommodation name into the api
        }
        $.get(url)
        .done(function(data) {
            bookingsMap(data, tableBody);
        })
        .fail(function( jqXHR, textStatus ) {
        });
    });
}


//get data from JSON and list of all games with game id, creation date, players emails
function bookingsMap(data, tableBody) {
 data.map(function(bookingData) {
     $(tableBody).empty(); //reset table contents
     var booking = {};
     booking.number = bookingData.bookingNumber;
     booking.guest = bookingData.guestName;
     booking.web = bookingData.reservationWebsite;
     booking.checkIn = bookingData.checkIn;
     booking.checkOut = bookingData.checkOut;
     booking.rating = bookingData.rating;
     booking.url = bookingData.reviewURL;
     booking.employee = bookingData.employee;
     //TODO: pass tableBody id through variable 'tableBody' if it should load in bcn or svq table
     $(tableBody).append("<tr>" + "<td>" + booking.number + "</td>" +
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
