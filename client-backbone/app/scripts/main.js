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


require([
    'backbone','routes/index'
], function (Backbone,IndexRouter) {
    new IndexRouter();
    Backbone.history.start();
});