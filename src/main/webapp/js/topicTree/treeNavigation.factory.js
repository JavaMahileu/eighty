/**
 * @ngdoc service
 * @name eightyQuestionsFactory.questionsFactory
 *
 * @description
 * A factory which shares question methods between controllers.
 */
(function () {
    'use strict';
    /*exported isPrintLog */
    angular
        .module('eightyFactories')
        .factory('treeNavigationFactory', treeNavigationFactory);

    treeNavigationFactory.$inject = ['$modal', 'crudFactory'];

    function treeNavigationFactory($modal, crudFactory) {

        var publicMethods = {
            getTreeTopics: getTreeTopics,
            editTopic: editTopic,
            addTopic: addTopic,
            deleteTopic: deleteTopic
        };
        return publicMethods;

        function getTreeTopics(topic) {
            var result = [];
            var firstSubtopics = topic.topics;
            firstSubtopics.forEach(function(entry) {
                var firstTreeTopic = {};
                firstTreeTopic.title = entry.title;
                firstTreeTopic.data = {};
                firstTreeTopic.data.id = entry.id;
                firstTreeTopic.topics = [];
                var secondSubtopics = entry.topics;
                secondSubtopics.forEach(function(subEntry) {
                    var secondTreeTopic = {};
                    secondTreeTopic.title = subEntry.title;
                    secondTreeTopic.data = {};
                    secondTreeTopic.data.id = subEntry.id;
                    firstTreeTopic.topics.push(secondTreeTopic);
                });
                result.push(firstTreeTopic);
            });
            return result;
        }

        function editTopic(node) {
            var modalInstance = $modal
                    .open({
                        templateUrl: 'pages/edittopic.html',
                        resolve: {
                            node: getNode
                        },
                        controller: 'editTopicInstanceCtrl as editTopicInstance'
                    });
            modalInstance.result.then(function(topic) {
                crudFactory.topic().get({id: node.data.id}).$promise.then(function(savedTopic) {
                    savedTopic.title = topic.title;
                    savedTopic.id = node.data.id;
                    crudFactory.topic().update(savedTopic).$promise.then(function() {
                        node.title = topic.title;
                    },
                    function(error) {
                        printLog(error);
                        errorAlert('Cannot edit topic!', $modal);
                    });
                }, function(error) {
                    printLog(error);
                });
            });

            function getNode() {
                return node;
            }
        }

        function addTopic(scope, node) {
            var id;
            if (node) {
                id = node.data.id;
            } else {
                id = 0;
            }
            var modalInstance = $modal
                    .open({
                        templateUrl: 'pages/addtopic.html',
                        controller: 'addTopicInstanceCtrl as addTopicInstance'
                    });
            modalInstance.result.then(function(newTopic) {
                crudFactory.topic().create({id: id}, newTopic).$promise.then(function(newTopic) {
                    newTopic.data = {};
                    newTopic.data.id = newTopic.id;
                    if (node) {
                        node.topics.push(newTopic);
                    } else {
                        scope.treedata.push(newTopic);
                    }
                    scope.treeControl.expand_branch(node);
                }, function(error) {
                    printLog(error);
                    errorAlert('Cannot add topic!', $modal);
                });
            }, function() {
            });
        }

        function deleteTopic(node, scope) {
            var modalInstance = $modal
                    .open({
                        templateUrl: 'pages/deletetopic.html',
                        controller: 'deleteTopicInstanceCtrl as deleteTopicInstance'
                    });
            modalInstance.result.then(function(topic) {
                topic.id = node.data.id;
                crudFactory.topic().remove(topic).$promise.then(function() {
                    var topics;
                    if (scope.treeControl.get_parent_branch(node)) {
                        topics = scope.treeControl.get_parent_branch(node).topics;
                    } else {
                        topics = scope.treedata;
                    }
                    scope.treeControl.select_parent_branch(node);
                    topics.splice(topics.indexOf(node), 1);
                }, function(error) {
                    printLog(error);
                    errorAlert('Cannot delete topic!', $modal);
                });

            }, function() {
            });
        }
    }
})();