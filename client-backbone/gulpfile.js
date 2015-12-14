var gulp = require('gulp');
var usemin = require('gulp-usemin');
var rev = require('gulp-rev');
var minifyHtml = require('gulp-minify-html');
var requireJsOptimize = require('gulp-requirejs-optimize');


var paths = {
	scripts: ['app/**/*.js'],
	dist: 'dist2/'
};

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
	'usemin',
	'scripts'
]);
