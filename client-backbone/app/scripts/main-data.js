require.config({
    baseUrl: 'scripts',
    paths: {
        jquery: '../bower_components/jquery/dist/jquery',
        backbone: '../bower_components/backbone/backbone',
        underscore: '../bower_components/lodash/dist/lodash',
        bootstrap: '../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap',
        bootstrap_datepicker: '../bower_components/bootstrap-datepicker/js/bootstrap-datepicker',
        bootstrap_datepicker_fr_CH: '../bower_components/bootstrap-datepicker/dist/locales/bootstrap-datepicker.fr-CH.min',
        fullcalendar: '../bower_components/fullcalendar/dist/fullcalendar',
        fullcalendar_lang_all : '../bower_components/fullcalendar/dist/lang-all',
        moment: '../bower_components/moment/moment',
		templates: '../../.tmp/scripts/templates'
    },
    shim: {
        bootstrap: {
            deps: ['jquery'],
            exports: 'jquery'
        },
        backbone: {
            deps: ['underscore', 'jquery'],
            exports: 'Backbone'
        },
        bootstrap_datepicker: {
            deps: ['jquery'],
            exports: '$'
        },
        fullcalendar_lang_all: {
            deps: ['fullcalendar'],
            exports: '$'
        }
    }
});

require(['main'], function() {

});
