/*global define*/

define([
    'jquery',
    'underscore',
    'backbone',
    'templates',
    'models/user'
], function ($, _, Backbone, JST, UserModel) {
    'use strict';

    var NewaccountView = Backbone.View.extend({
        template: JST['app/scripts/templates/newaccount.ejs'],

        tagName: 'div',

        id: '',

        className: '',

        events: {
            'click #createNewAccount' : 'doCreate'
        },

        el : '#modals',

        initialize: function () {
            this.render();
        },

        render: function () {
            var that = this;
            this.$el.html(this.template());
            this.modalNewAccount = $('#modalNewAccount');
            this.firstNameLastName = $('#firstNameLastName');
            this.username = $('#username');
            this.password = $('#password');
            this.modalNewAccount.on('hidden.bs.modal', function() {
                that.remove();
            });
            this.modalNewAccount.on('shown.bs.modal', function (e) {
                that.firstNameLastName.focus();
            })
        },

        open: function() {
            this.modalNewAccount.modal();
        },

        serialize: function() {
            return {
                firstNameLastName : this.firstNameLastName.val(),
                username : this.username.val(),
                password : this.password.val()
            };
        },
        doCreate : function() {
            var that = this;
            var user = new UserModel(this.serialize());
            user.save(null, {
                success : function() {
                    that.trigger('logged',true, user.firstNameLastName);
                    alert('Ton compte a bien été créé');
                }
            });
            this.modalNewAccount.modal('hide');
        },
        remove : function() {
                this.$el.empty().off();
                this.stopListening();
                return this;
            }
    });

    return NewaccountView;
});
