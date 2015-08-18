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

        el : '#divNewAccount',

        initialize: function () {
            this.render();
        },

        render: function () {
            this.$el.html(this.template());
            this.modalNewAccount = $('#modalNewAccount');
            this.firstNameLastName = $('#firstNameLastName');
            this.username = $('#username');
            this.password = $('#password');
        },

        open: function() {
            console.log(this.modalNewAccount);
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
            var user = new UserModel(this.serialize());
            user.save();
        }
    });

    return NewaccountView;
});
