/*global define*/

define([
    'jquery',
    'backbone',
    'views/index'
], function ($, Backbone,IndexView) {
    'use strict';

    var IndexRouter = Backbone.Router.extend({
        routes: {
        	"" : "index"
        },
        index : function() {
        	new IndexView();
        }

    });

    return IndexRouter;
});
