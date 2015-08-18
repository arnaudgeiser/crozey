/*global require*/
'use strict';

require.config({
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
        moment: '../bower_components/moment/moment'
    }
});

require([
    'backbone','routes/index'
], function (Backbone,IndexRouter) {
    new IndexRouter();
    Backbone.history.start();
});

require(['backbone','jquery'], function(Backbone,$) {
    Backbone.$.ajaxSetup({
        statusCode : {
            401 : function() {
                alert("Tu n'es pas authentifié. Commence par créer un compte s'il te plait !");
            }
        },
        contentType: "application/json",
        xhrFields: {
            withCredentials: true
        }
    });
});

require([
    'jquery',
    'bootstrap_datepicker'
], function($) {
    $.fn.datepicker.defaults.autoclose = true;
    $.fn.datepicker.defaults.format = 'dd/mm/yyyy';
    $.fn.datepicker.defaults.language = 'fr';
});