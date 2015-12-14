var gulp = require('gulp');
var usemin = require('gulp-usemin');
var rev = require('gulp-rev');
var minifyHtml = require('gulp-minify-html');
var requireJsOptimize = require('gulp-requirejs-optimize');
var install = require('gulp-install');

var paths = {
	scripts: ['app/**/*.js'],
	dist: 'dist2/'
};

gulp.task('install', function(cb) {
	gulp.src(['./bower.json','./package.json']).pipe(install({production: true}));
	cb();
});

gulp.task('scripts', function() {
	return gulp.src('app/scripts/main.js')
		.pipe(requireJsOptimize({
			mainConfigFile : 'app/scripts/main-data.js'
		}))
		.pipe(gulp.dest(paths.dist));
});


gulp.task('usemin', function() {
	return gulp.src('./app/*.html')
		.pipe(usemin())
		.pipe(gulp.dest(paths.dist));
});


gulp.task('build',[
	'install',
	'usemin',
	'scripts'
]);
