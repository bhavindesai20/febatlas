var gulp = require('gulp');
var inject = require('gulp-inject');
var bower_files  = require('main-bower-files');
var clean = require('gulp-clean');
var browserSync = require('browser-sync').create();
var angularFilesort = require('gulp-angular-filesort');
var uglify = require('gulp-uglify');
var cleanCss = require('gulp-clean-css');
var concat = require('gulp-concat');
var filter = require('gulp-filter');
var merge = require('merge-stream');
var config = {
    paths: {
        src: './ui-seed',
        dist: './.dist',
        bower: './bower_components',

    }

};

gulp.task('clean',function(){
    return gulp.src(config.paths.dist,{read:false})
        .pipe(clean());
});

gulp.task('inject',function(){
    var cssfiles = gulp.src([
        './ui-seed/**/*.css'], {read: false});

    var jsfiles = gulp.src([
        './ui-seed/**/*.js'])
        .pipe(angularFilesort());

    return gulp.src(config.paths.src + '/home.html')
        .pipe(inject(gulp.src(bower_files(), {read: false}),
            {name:'bower'}))
        .pipe(inject(cssfiles))
        .pipe(inject(jsfiles))
        .pipe(gulp.dest(config.paths.dist));
});

gulp.task('serve',['inject'],function(){
    browserSync.init({
        port: 5002,
        server: {
            baseDir:[config.paths.dist,config.paths.bower,config.paths.src],
            routes: {
                '/bower_components': "bower_components"
            }
        },
        files: [
            config.paths.src
        ]
    });

});

//build
gulp.task('minifyCss',function () {
    var vendorStyles = gulp.src(bower_files())
        .pipe(filter(['**/*.css']))
        .pipe(concat('vendor.min.css'))
        .pipe(cleanCss())
        .pipe(gulp.dest(config.paths.dist + '/styles'));

    var appStyles = gulp.src(config.paths.src + '/**/*.css')
        .pipe(concat('app.min.css'))
        .pipe(cleanCss())
        .pipe(gulp.dest(config.paths.dist + '/styles'));

    return merge(vendorStyles, appStyles);
});

gulp.task('minifyJs',function () {
    var vendorJs = gulp.src(bower_files())
        .pipe(filter(['**/*.js']))
        .pipe(concat('vendor.min.js'))
        .pipe(uglify())
        .pipe(gulp.dest(config.paths.dist + '/scripts'));

    var appJs = gulp.src(config.paths.src + '/**/*.js')
        .pipe(angularFilesort())
        .pipe(concat('app.min.js'))
        .pipe(uglify())
        .pipe(gulp.dest(config.paths.dist + '/scripts'));

    return merge(vendorJs, appJs);
});

gulp.task('htmls', function(){
    return gulp.src([config.paths.src + '/**/*.html',
    '|' + config.paths.src + '/index.html'])
        .pipe(gulp.dest(config.paths.dist));

});

gulp.task('others', function(){
    return gulp.src([config.paths.src + '/**/*.*',
            '!**/*.html',
            '!**/*.js',
            '!**/*.css'
    ])
        .pipe(gulp.dest(config.paths.dist));

});

gulp.task('build',['minifyCss','minifyJs','htmls','others'], function() {
    var vendorFiles = gulp.src([
        config.paths.dist + './styles/vendor.min.css',
        config.paths.dist + './scripts/vendor.min.js'],
    {read: false});

    var appFiles = gulp.src([
        config.paths.dist + './styles/app.min.css',
        config.paths.dist + './scripts/app.min.js'],
    {read: false});

    return gulp.src(config.paths.src + '/home.html')
        .pipe(inject(vendorFiles, {name: 'vendor.min'}))
        .pipe(inject(appFiles, {name: 'app.min'}))
        .pipe(gulp.dest(config.paths.dist));
});