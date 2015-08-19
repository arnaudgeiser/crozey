/*global define*/

define([
    'jquery',
    'underscore',
    'bootstrap',
    'backbone',
    'templates',
    'views/login',
    'views/event',
    'views/newaccount',
    'fullcalendar',
    'moment'
    ], function ($, _,Boostrap, Backbone, JST, LoginView, EventView, NewAccountView) {
        'use strict';
        var FEED_URL = 'http://localhost:8080/reservations/feed';

        var currentYear = new Date().getFullYear();
        var IndexView = Backbone.View.extend({
            template: JST['app/scripts/templates/index.ejs'],

            tagName: 'div',

            id: '',

            className: '',

            events: {
                "click #login" : "openLoginDialog",
                "click #logout" : "unlog",
                "click #newAccount" : "openNewAccountDialog"
            },

            el : '#body',

            initialize: function () {
                this.render();
                this.login = $('#login');
                this.logout = $('#logout');
                this.calendar = $('#calendar');
                this.initCalendar();
                this.checkCredentials();
            },

            initCalendar: function() {
                var that = this;
                this.calendar.fullCalendar({
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
                        that.eventView = new EventView();
                        that.eventView.openForAdd(date);
                        that.listenTo(that.eventView, 'change', that.refreshEvents)
                    },
                    eventClick : function(event, jsEvent, view) {
                        that.eventView = new EventView();
                        that.eventView.openForEdit(event);
                        that.listenTo(that.eventView, 'change', that.refreshEvents)
                    }
                });
            },
            render: function () {
                this.$el.html(this.template());
            },
            checkCredentials : function() {
                var that = this;
                $.ajax({
                    url : 'http://localhost:8080/authentication/logged',
                    success : function(logged) {
                        that.loggedCallback(logged);
                    }
                });                
            },
            loggedCallback : function(logged) {
                if(logged) {
                    this.login.hide();
                    this.logout.show();
                } else {
                    this.login.show();
                    this.logout.hide();
                }
            },
            openLoginDialog : function() {
                this.loginView = new LoginView();
                this.listenTo(this.loginView,'logged',this.loggedCallback);
                this.loginView.open();
            },
            unlog : function() {
                var that = this;
                $.ajax({
                    url : 'http://localhost:8080/authentication/logout',
                    method : 'POST',
                    success : function() {
                        that.loggedCallback(false);
                    }
                });
            },
            refreshEvents : function() {
                this.eventView.close();
                this.calendar.fullCalendar('refetchEvents');
            },
            openNewAccountDialog : function() {
                this.newAccountView = new NewAccountView();
                this.newAccountView.open();
            }
        });

return IndexView;
});
