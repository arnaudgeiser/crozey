require.config({
    baseUrl: 'scripts',
    shim: {
        bootstrap: {
            deps: ['jquery'],
            exports: 'jquery'
        }
    },
    paths: {
        jquery: '../bower_components/jquery/dist/jquery',
        backbone: '../bower_components/backbone/backbone',
        underscore: '../bower_components/lodash/dist/lodash',
        bootstrap: '../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap',
        bootstrap_datepicker: '../bower_components/bootstrap-datepicker/js/bootstrap-datepicker',
        bootstrap_datepicker_fr_CH: '../bower_components/bootstrap-datepicker/dist/locales/bootstrap-datepicker.fr-CH.min',
        fullcalendar: '../bower_components/fullcalendar/dist/fullcalendar',
        fullcalendar_lang_all : '../bower_components/fullcalendar/dist/lang-all',
        moment: '../bower_components/moment/moment'
    }
});

require(['main'], function() {

});