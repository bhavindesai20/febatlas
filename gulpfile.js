var gulp = require('gulp');
var inject = require('gulp-inject');
var bower_files  = require('main-bower-files');
var clean = require('gulp-clean');
var browserSync = require('browser-sync').create();

var config = {
    paths: {
        src: './WebContent/WEB-INF/views',
        dist: './.dist',
        bower: './bower_components',

    }

};

gulp.task('clean',function(){
    return gulp.src(config.paths.dist,{read:false})
        .pipe(clean());
});

gulp.task('inject',function(){
    var sources = gulp.src([
        './WebContent/WEB-INF/resource/**/*.js',
        './WebContent/WEB-INF/resource/**/*.css'
    ],{read: false});

    return gulp.src(config.paths.src + '/home.html')
        .pipe(inject(gulp.src(bower_files(), {read: false}),
            {name:'bower'}))
        .pipe(inject(sources))
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