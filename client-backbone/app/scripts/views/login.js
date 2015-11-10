/*global define*/

define([
    'jquery',
    'underscore',
    'backbone',
    'templates'
], function ($, _, Backbone, JST) {
    'use strict';

    var LOGIN_URL = config.url + '/authentication/login';

    var LoginView = Backbone.View.extend({
        template: JST['app/scripts/templates/login.ejs'],

        tagName: 'div',

        id: '',

        className: '',

        events: {
            "click #connexion" : "connection"
        },

        el : "#modals",

        initialize: function () {
            var that = this;
            this.render();
            this.modalLogin = $('#modalLogin');
            this.username = $('#username');
            this.modalLogin.on('hidden.bs.modal', function() {
                this.remove();
            })
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
                success : function(o) {
                    that.trigger('logged',o.logged, o.name);
                    that.close();
                },
                error : function(e) {
                    alert("Es-tu sur de ton mot de passe ?");
                }
            });
        },
        open : function() {
            var that = this;
            this.modalLogin.modal('show');
            this.modalLogin.on('shown.bs.modal', function (e) {
                that.username.focus();
            })
        },
        close : function() {
            this.modalLogin.modal('hide');
        },
        remove : function() {
            this.$el.empty().off();
            this.stopListening();
            return this;
        }
    });

    return LoginView;
});
