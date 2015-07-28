define(['jquery','fullcalendar','moment','bootstrap','bootstrap_datepicker','bootstrap_datepicker_fr_CH'], function($) {
	'use strict'

	var BASE_URL = 'http://localhost:8080/reservations';
	var FEED_URL = 'http://localhost:8080/reservations/feed';
	var LOGIN_URL = 'http://localhost:8080/authentication/login';
	var LOGOUT_URL = 'http://localhost:8080/authentication/logout';

	var currentYear = new Date().getFullYear();

	var $calendar = $('#calendar');
	var $modalAddEvent = $('#modalAddEvent');
	var $modalLogin = $('#modalLogin');

	var $reserver = $('#reserver');
	var $supprimer = $('#supprimer');
	var $login = $('#login');
	var $logout = $('#logout');
	var $connexion = $('#connexion');

	var $username = $('#username');
	var $password = $('#password');

	var $id = $('#id');
	var $title = $('#title');
	var $from = $('#from');
	var $to = $('#to');
	var $private = $('#private');

	$calendar.fullCalendar({
	    fixedWeekCount: false,
	    contentHeight: 500,
	    lazyFetching: false,
	    defaultDate: $.fullCalendar.moment(currentYear+'-12-01'),
	    lang : 'fr',
	    eventSources : [{
	        url: FEED_URL,
	    }],
	    eventRender : function(event, element, view) {
	    	var html = '<b>'+event.title+'</b><br />'+event.username;
	    	element.empty().append(html);
	    },
	    dayClick : function(date, jsEvent, view) {	    	
	        var stringMoment = date.format('DD.MM.YYYY');

	        $id.val('');
	        $from.datepicker('update',stringMoment);
	        $to.datepicker('update',stringMoment);
	        $modalAddEvent.modal();
	        $supprimer.hide();
	        $reserver.text('Réserver');

	    },
	    eventClick : function(event, jsEvent, view) {
	    	var start = event.start;
        	var end = event.end || start;
        	var end = end.subtract(1,'d');

	    	$title.val(event.title);
	    	$from.val(start.format('DD.MM.YYYY'));
	    	$to.val(end.format('DD.MM.YYYY'));
	    	$id.val(event.id);
	    	$modalAddEvent.modal();
	    	$supprimer.show();
	    	$reserver.text('Modifier');
	    }
	});

	$supprimer.click(function() {
		var eventObject = retrieveEvent();
		reservationService.remove(eventObject);
	});

	$reserver.click(function() {
		var eventObject = retrieveEvent();
		if(validator.isValid()) {
			reservationService.persist(eventObject);
		}
	});

	$login.click(function() {
		$modalLogin.modal();
	});

	var logged = false;

	$logout.click(function() {
		$.ajax({
			url : LOGOUT_URL,
			method : 'POST',
			success : function() {
				logged = false;
				$login.show();
				$logout.hide();
			}
		});
	});

	$connexion.click(function() {
		var user = {
			username : $username.val(),
			password : $password.val()
		};

		console.log(JSON.stringify(user));

		$.ajax({
			url :  LOGIN_URL,
			method : 'POST',
			data : JSON.stringify(user),
			contentType: "application/json",
			success : function() {
				$modalLogin.modal('hide');
				logged = true;
				$login.hide();
				$logout.show();
			},
			error : function(e) {
				alert("Echec de l'authentication");
			},
			xhrFields: {
    			  withCredentials: true
   			}
		});
	});

	function retrieveEvent() {
		var id = $id.val();
		var title = $title.val();
		var from = $from.datepicker('getDate').toISOString();
		var to = $to.datepicker('getDate').toISOString();
		var isPrivate = $private.prop('checked');
		var eventObject = {
			id : id,
			title : title,
			period : {
				from : from,
				to : to
			},
			privacy : isPrivate,
		};
		return eventObject;
	}

	var reservationService = (function() {
		function persist(eventObject) {
			if(eventObject.id) {
				update(eventObject);
			} else {
				add(eventObject);
			}
		}

		function add(eventObject) {
			dbAction(eventObject, 'POST');
		}

		function update(eventObject) {
			dbAction(eventObject, 'PUT');
		}

		function remove(eventObject) {
			console.log('remove');
			dbAction(eventObject, 'DELETE');
		}

		function dbAction(eventObject, method) {
			var method = 
			$.ajax({
				url : BASE_URL,
				data : JSON.stringify(eventObject),
				contentType: "application/json",
				method : method,
				success : function() {
					$calendar.fullCalendar('refetchEvents');
					$modalAddEvent.modal('hide');
				},
				error : function(jqXHR, textStatus, errorThrown ) {
					$calendar.fullCalendar('refetchEvents');
					if(jqXHR.status==404) {
						alert("Pas authentifié !")
					}
				},
				xhrFields: {
    			  withCredentials: true
   				},
   				crossDomain: true
			});
		}

		return {
			persist : persist,
			remove : remove
		}
	})();

	var validator = (function () {
		function isValid() {
			var eventObject = retrieveEvent();
			console.log('isValid');
			if(eventObject.title.length==0 || eventObject.period.from.length==0 || eventObject.period.to.length==0) {
				return false;
			}
			return true;
		}
		return {isValid : isValid};
	})();
});