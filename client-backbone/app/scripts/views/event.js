/*global define*/

define([
    'jquery',
    'underscore',
    'backbone',
    'templates',
    'moment',
    'models/event',
    'bootstrap_datepicker',
    'bootstrap_datepicker_fr_CH'
], function ($, _, Backbone, JST,moment, EventModel) {
    'use strict';

    var EventView = Backbone.View.extend({
        template: JST['app/scripts/templates/event.ejs'],

        tagName: 'div',

        id: '',

        className: '',

        events: {
            'click #reserver' : 'doReservation',
            'click #supprimer' : 'doSuppression'
        },

        el: '#modals',

        initialize: function () {
            var that = this;
            this.render();
            this.modalAddEvent = $('#modalAddEvent');
            this.id = $('#id');
            this.from = $('#from').datepicker();
            this.to = $('#to').datepicker();
            this.title = $('#title');
            this.private = $('#private');
            this.reserver = $('#reserver');
            this.supprimer = $('#supprimer');
            this.modalAddEvent.on('hidden.bs.modal', function() {
                that.remove();
            });

        },
        openForAdd : function(date) {
            var that = this;
            var stringMoment = date.format('DD.MM.YYYY');
            this.id.val('');
            this.from.datepicker('update',stringMoment);
            this.to.datepicker('update',stringMoment);
            this.reserver.text('Réserver');
            this.supprimer.hide();
            this.modalAddEvent.modal();
            this.modalAddEvent.on('shown.bs.modal', function (e) {
                that.title.focus();
            })
        },
        openForEdit : function(event) {
            var that = this;
            var start = moment(event.start);
            var end = moment(event.end || start);
            var end = end.subtract(1,'d');

            this.id.val(event.id);
            this.title.val(event.title);
            this.from.datepicker('update',start.format('DD.MM.YYYY'));
            this.to.datepicker('update', end.format('DD.MM.YYYY'));
            this.private.prop('checked',event.private);
            this.supprimer.show();
            this.reserver.text('Modifier');
            this.modalAddEvent.on('shown.bs.modal', function (e) {
                that.title.select().focus();
            })
            this.modalAddEvent.modal();
        },
        close : function() {
            this.modalAddEvent.modal('hide');
        },
        render: function () {
            this.$el.html(this.template());
        },
        serialize: function() {
            var event = {
                title : this.title.val(),
                period : {
                    from : this.from.datepicker('getDate').toISOString(),
                    to : this.to.datepicker('getDate').toISOString()
                },
                privacy : this.private.prop('checked')
            }
            var id = this.id.val();

            if(id.length>0) {
                event.id = id;
            }
            return event;
        },
        doReservation: function() {
            var that = this;
            var eventModel = new EventModel(this.serialize());
            eventModel.save(null,{
                success : function() {
                    that.trigger('change');
                    that.modalAddEvent.modal('hide');
                }
            });
        },
        doSuppression : function() {
            var that = this;
            var eventModel = new EventModel(this.serialize());
            eventModel.destroy({
                success : function() {
                    that.trigger('change')
                    that.modalAddEvent.modal('hide');
                }
            });
        },
        remove : function() {
                this.$el.empty().off();
                this.stopListening();
                return this;
        }
    });

    return EventView;
});
