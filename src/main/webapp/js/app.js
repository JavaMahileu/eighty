'use strict';
/**
 * @ngdoc overview
 * @name eightyApp
 *
 * @description
 * A main module for configuring `ui.router`.
 */
angular.module('eightyApp', [
    'ngResource',
    'ui.router',
    'ui.bootstrap',
    'ngAnimate',
    'ngTagsInput',
    'eightyDirectives',
    'eightyControllers',
    'eightyFactories',
    'eightyFilters'
]);
angular.module('eightyFactories', ['ngResource']);
angular.module('eightyDirectives', []);
angular.module('eightyControllers', ['ui.router', 'ui.bootstrap']);

(function() {
     angular
         .module('eightyApp')
         .config(config);

    config.$inject = ['$stateProvider', '$urlRouterProvider'];
    function config($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise('/home');
        $stateProvider.
            state('root', {
                abstract: true,
                views: {
                    'tag': {
                        templateUrl: 'pages/tags.html',
                        controller: 'tagsCtrl as tags',
                        resolve: {
                            tagsList: getTopNTags
                        }
                    },
                    'customer': {
                        templateUrl: 'pages/customers.html',
                        controller: 'customersCtrl as customers',
                        resolve: {
                            customersList: getCustomers
                        }
                    }
                }
            }).
            state('home', {
                url: '/home',
                views: {
                    'main@': { templateUrl: 'pages/app.html' }
                },
                parent: 'root'
            }).
            state('export', {
                url: '/export',
                views: {
                    'main@': { templateUrl: 'pages/export.html', controller: 'topicQuestionsCtrl as questionsCtrl' }
                },
                parent: 'root'
            }).
            state('questionsWithTag', {
                url: '/questionsWithTag/{tagName}',
                views: {
                    'main@': { templateUrl: 'pages/filteredQuestions.html', controller: 'filteredQuestionsCtrl as filtered'}
                },
                resolve: {
                    questionsFromServer: getAllQuestionsWithTag
                },
                parent: 'root'
            }).
            state('topics', {
                url: '/topics/:id',
                views: {
                    'main@': { templateUrl: 'pages/questions.html', controller: 'topicQuestionsCtrl as questionsCtrl'}
                },
                parent: 'root'
            }).
            state('questionsFromCustomer', {
                url: '/questionsFromCustomer/{customerName}',
                views: {
                    'main@': { templateUrl: 'pages/filteredQuestions.html', controller: 'filteredQuestionsCtrl as filtered'}
                },
                parent: 'root',
                resolve: {
                    questionsFromServer: getAllQuestionsFromCustomer
                }
            });
    }

    getTopNTags.$inject = ['tagsFactory'];
    function getTopNTags(tagsFactory) {
        return tagsFactory.getTopNTags();
    }

    getCustomers.$inject = ['customersFactory'];
    function getCustomers(customersFactory) {
        return customersFactory.getCustomers();
    }

    getAllQuestionsWithTag.$inject = ['$stateParams', 'questionsFactory'];
    function getAllQuestionsWithTag($stateParams, questionsFactory) {
        var questions = questionsFactory.getAllQuestionsWithTag($stateParams);
        questions.then(undefined, function (error) {
            printLog(error);
        });
        return questions;
    }

    getAllQuestionsFromCustomer.$inject = ['$stateParams', 'questionsFactory'];
    function getAllQuestionsFromCustomer($stateParams, questionsFactory) {
        var questions = questionsFactory.getAllQuestionsFromCustomer($stateParams);
        questions.then(undefined, function (error) {
            printLog(error);
        });
        return questions;
    }
})();

