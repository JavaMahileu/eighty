module.exports = function (config) {
    config.set({

        basePath: '../',

        files: [
            '../main/webapp/bower_components/jquery/jquery.js',
            '../main/webapp/bower_components/bootstrap/dist/js/bootstrap.js',
            '../main/webapp/bower_components/angular/angular.js',
            '../main/webapp/bower_components/angular-animate/angular-animate.js',
            '../main/webapp/bower_components/angular-resource/angular-resource.js',
            '../main/webapp/bower_components/angular-ui-router/release/angular-ui-router.js',
            '../main/webapp/bower_components/angular-bootstrap/ui-bootstrap.js',
            '../main/webapp/bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
            '../main/webapp/bower_components/ng-tags-input/ng-tags-input.min.js',
            '../main/webapp/bower_components/angular-mocks/angular-mocks.js',
            '../main/webapp/js/**/*.js',
            'javascript/unit/**/*.js'
        ],

        autoWatch: true,

        frameworks: ['jasmine'],

        browserNoActivityTimeout: 30000,

        browsers: ['Firefox'],

        plugins: [
            'karma-firefox-launcher',
            'karma-jasmine',
            'karma-coverage'
        ],

        singleRun: true,
        reporters: [/*'progress', */'coverage', 'dots'],

        preprocessors: {
            '../main/webapp/js/**/*.js': ['coverage'],
            'client/*': ['browserify']
        },

        coverageReporter: {
            reporters: [
                {type: 'lcovonly', dir: '../../target/site/cobertura/'},
                {type : 'cobertura', dir: '../../target/site/cobertura/'}/*,
                {type : 'html', dir : '../../target/site/coverage/', file : 'coverage.txt'}*/
            ]
        }

    });
};