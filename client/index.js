/*requirejs(['./app'], function(app) {
	alert('ok');
});*/
requirejs(['./app.js'], function(app) {
	requirejs(['main.js']);
});