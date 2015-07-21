define(['jquery','fullcalendar','moment','bootstrap','bootstrap_datepicker','bootstrap_datepicker_fr_CH'], function($) {
	'use strict'

	var currentYear = new Date().getFullYear();

	var $calendar = $('#calendar');

	$calendar.fullCalendar({
	    fixedWeekCount: false,
	    contentHeight: 500,
	    lazyFetching: false,
	    defaultDate: $.fullCalendar.moment(currentYear+'-12-01'),
	    lang : 'fr',
	    eventSources : [{
	        url: 'http://localhost:8080/reservations/feed',
	    }],
	    loading : function( isLoading, view ) {
	    },
	    eventRender : function(event, element, view) {
	    },
	    dayClick : function(date, jsEvent, view) {
	        var stringMoment = date.format('DD.MM.YYYY');
	        $('.periode').val(stringMoment);
	        $('#myModal').modal()
	    }
	});

	$('#reserver').click(function() {
		title = $('#title').val();
		from = $('#from').datepicker('getDate').toISOString();
		to = $('#to').datepicker('getDate').toISOString();
		isPrivate = $('#private').prop('checked');
		var newEvent = {
			title : title,
			period : {
				from : from,
				to : to
			},
			'private' : isPrivate,
		};
		$.ajax({
			url : 'http://localhost:8080/reservations/add',
			data : JSON.stringify(newEvent),
			contentType: "application/json",
			method : 'POST',
			success : function() {
				$calendar.fullCalendar('refetchEvents');
			},
			error : function() {
				$calendar.fullCalendar('refetchEvents');
			}
		});
	});
});