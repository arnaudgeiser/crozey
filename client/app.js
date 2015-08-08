requirejs.config({
	baseUrl: 'bower_components',
	paths : {
		jquery : './jquery/dist/jquery.min',
		moment : './moment/min/moment.min',
		fullcalendar : './fullcalendar/dist/fullcalendar',
		fullcalendar_all_lang : './fullcalendar/dist/lang-all',
		bootstrap : './bootstrap/dist/js/bootstrap.min',
		bootstrap_datepicker : './bootstrap-datepicker/dist/js/bootstrap-datepicker',
		bootstrap_datepicker_fr_CH : './bootstrap-datepicker/dist/locales/bootstrap-datepicker.fr-CH.min'
	}
});

require(['jquery','bootstrap_datepicker'], function($) {
	$.fn.datepicker.defaults.autoclose = true;
	$.fn.datepicker.defaults.format = 'dd/mm/yyyy';
	$.fn.datepicker.defaults.language = 'fr';
});