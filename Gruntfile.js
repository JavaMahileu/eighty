'use strict';

var mountFolder = function (connect, dir) {
    return connect.static(require('path').resolve(dir));
};

var fillTopics = function (data) {
    if (data.topics) {
        data.topics.forEach(function (entry) {
            entry.id = entry.id || 9999;
            entry.topics = entry.topics || [];
            entry.questions = entry.questions || [];
        });
    }
};

var restMiddleware = function (req, res, next) {
    if (req.method === 'PUT') {
        var body = {};
        req.on('data', function (chunk) {
            body = JSON.parse(chunk.toString());
            fillTopics(body);
        });
        req.on('end', function () {
            res.writeHead(200, 'OK', {'Content-Type': 'application/json'});
            res.end(JSON.stringify(body));
        });
    } else if (req.method === 'DELETE') {
        res.writeHead(200, 'OK');
        res.end();
    } else {
        return next();
    }

};

module.exports = function (grunt) {
    
    var protractorBrowserPort = grunt.option('p:port') || '8282';

    grunt.initConfig({

        pkg: grunt.file.readJSON('package.json'),

        // Make sure code styles are up to par and there are no obvious mistakes
        jshint: {
            options: {
                jshintrc: '.jshintrc',
                reporter: require('jshint-stylish')
            },
            all: {
                src: [
                    'Gruntfile.js',
                    'src/main/webapp/js/**/*.js'
                ]
            }
        },
        processhtml: {
            options: {
                strip: true
            },
            e2eTests: {
                files: {
                    'src/main/webapp/index.html': ['src/main/webapp/templates/htmlTemplates/base.html']
                }
            },
            production: {
                files: {
                    'src/main/webapp/index.html': ['src/main/webapp/templates/htmlTemplates/base.html']
                }
            },
            development: {
                files: {
                    'src/main/webapp/index.html': ['src/main/webapp/templates/htmlTemplates/base.html']
                }
            }
        },
        ngdocs: {
            options: {
                dest: 'src/main/webapp/angularDocs',
                title: 'Eighty Angular Docs',
                scripts: ['angular.js'],
                html5Mode: false,
                navTemplate: 'src/main/webapp/pages/angularDocsNav.html'
            },
            api: {
                src: ['src/**/*.js', '!src/**/*.spec.js', '!src/**/bower_components/**/*.js'],
                title: 'API Documentation'
            }
        },
        clean: {
            angular: ['src/main/webapp/angularDocs'],
            e2etests: ['src/main/webapp/e2etestresults'],
            jsmin: ['src/main/webapp/js/min/'],
            cssmin: ['src/main/webapp/css/min/']
        },
        karma: {
            unit: {
                configFile: 'src/test/javascript/karma.conf.js',
                browsers: ['Firefox'],
                singleRun: true
            }
        },
        connect: {
            livereload: {
                options: {
                    //keepalive: true,
                    port: protractorBrowserPort,
                    base: ['src/main/webapp', 'src/test/resources/json/root', 'src/test/resources/json'],
                    middleware: function (connect, options) {
                        return [
                            restMiddleware,
                            mountFolder(connect, options.base[0]),
                            mountFolder(connect, options.base[1]),
                            mountFolder(connect, options.base[2])
                        ];
                    }
                }
            }
        },
        copy: {
            lcov: {
                expand: true,
                src: 'target/site/cobertura/**',
                dest: 'target/',
                flatten: true,
                filter: 'isFile'
            }
        },
        uglify: {
            options: {
                banner: '/*! <%= pkg.name %> - v<%= pkg.version %> - ' +
                    '<%= grunt.template.today("yyyy-mm-dd") %> */'
            },
            target: {
                files: {
                    'src/main/webapp/js/min/script.min.js': ['src/main/webapp/js/min/script.clean.js']
                }
            }
        },
        cssmin: {
            add_banner: {
                options: {
                    banner: '/*! <%= pkg.name %> - v<%= pkg.version %> - ' +
                        '<%= grunt.template.today("yyyy-mm-dd") %> */'
                },
                files: {
                    'src/main/webapp/css/min/style.min.css': ['src/main/webapp/css/abn_tree.css', 'src/main/webapp/css/app.css']
                }
            }
        },
        removelogging: {
            dist: {
                files: {
                    'src/main/webapp/js/min/script.clean.js': ['src/main/webapp/js/app.js', 'src/main/webapp/js/config.js',
                        'src/main/webapp/js/**/*.directive.js', 'src/main/webapp/js/**/*.factory.js', 'src/main/webapp/js/**/*.controller.js', 'src/main/webapp/js/filter.js', 'src/main/webapp/js/utility.js']
                }
            }
        },
        protractor : {
            options : {
                keepAlive : false,
                noColor : false,
                nodeBin : 'node/node'
            },
            all : {
                options : {
                    configFile : 'src/test/javascript/protractor.conf.js',
                    args : {
                        baseUrl : 'http://localhost:' + protractorBrowserPort
                    }
                }
            },
        }
    });

    grunt.loadNpmTasks('grunt-ngdocs');
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-karma');
    grunt.loadNpmTasks('grunt-contrib-connect');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-ng-annotate');
    grunt.loadNpmTasks('grunt-processhtml');
    grunt.loadNpmTasks('grunt-remove-logging');
    grunt.loadNpmTasks('grunt-protractor-runner');

    grunt.registerTask('minify', ['clean:jsmin', 'removelogging', 'uglify', 'clean:cssmin', 'cssmin']);
    grunt.registerTask('p:test', ['clean:e2etests','processhtml:e2eTests', 'connect', 'protractor', 'processhtml:production']);
    grunt.registerTask('k:test', ['processhtml:development', 'karma', 'copy:lcov']);

};
