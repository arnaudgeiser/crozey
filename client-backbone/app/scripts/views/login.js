/*global define*/

define([
    'jquery',
    'underscore',
    'backbone',
    'templates'
], function ($, _, Backbone, JST) {
    'use strict';

    var LOGIN_URL = 'http://localhost:8080/authentication/login';

    var LoginView = Backbone.View.extend({
        template: JST['app/scripts/templates/login.ejs'],

        tagName: 'div',

        id: '',

        className: '',

        events: {
            "click #connexion" : "connection"
        },

        el : "#divLogin",

        initialize: function () {
            this.render();
            this.modalLogin = $('#modalLogin');
        },

        render: function () {
            this.$el.html(this.template());
        },

        connection : function() {
            var that = this;
            var user = {
                username : $('#username').val(),
                password : $('#password').val()
            };

            $.ajax({
                url :  LOGIN_URL,
                method : 'POST',
                data : JSON.stringify(user),
                success : function() {
                    that.trigger('logged',[true]);
                    that.close();
                },
                error : function(e) {
                    alert("Es-tu sur de ton mot de passe ?");
                }
            });
        },
        open : function() {
            this.modalLogin.modal();
        },
        close : function() {
            this.modalLogin.modal('hide');
        }
    });

    return LoginView;
});
