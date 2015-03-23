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
            deleteTopic: deleteTopic,
            reloadTopics: reloadTopics
        };
        return publicMethods;

       function getTreeTopics(topic) {
           return getTopicWithTopics(topic).topics;
        }

       function getTopicWithTopics(topic) {
           for(var i = 0; i < topic.topics.length; i++) {
               topic.topics[i].data = {};
               topic.topics[i].data.id = topic.topics[i].id;
               delete topic.topics[i].id;
               delete topic.topics[i].questions;
               getTopicWithTopics(topic.topics[i]);
               if(topic.topics[i].topics.length === 0) {
                   delete topic.topics[i].topics;
               }
           }
           return topic;
        }

       function reloadTopics(treeNode, vm) {
           errorAlert('Topic is not found!', $modal);
           var treeNodesToTheRoot = getPathOfNodes(treeNode, vm);
           crudFactory.topic().getLastNotRemoved(treeNodesToTheRoot).$promise.then(function(topicIdResource) {
               var topicId = parseInt(topicIdResource[0]);
               crudFactory.topic().getPath({id: topicId}).$promise.then(function(topic) {
                   vm.treedata = getTreeTopics(topic);
                   var node;
                   for(var i = treeNodesToTheRoot.length - 1; i >= treeNodesToTheRoot.indexOf(topicId); i--) {
                       if(node === undefined) {
                           node = getChildNode(vm.treedata, treeNodesToTheRoot[i]);
                       } else {
                           var childrenNodes = vm.treeControl.get_children(node);
                           node = getChildNode(childrenNodes, treeNodesToTheRoot[i]);
                       }
                       vm.treeControl.expand_branch(node);
                   }
                }, function(error) {
                    printLog(error);
                });
           });
       }

       function getPathOfNodes(treeNode, vm) {
           var nodeIds = [];
           while(vm.treeControl.get_parent_branch(treeNode) !== null && vm.treeControl.get_parent_branch(treeNode) !== undefined) {
               nodeIds.push(vm.treeControl.get_parent_branch(treeNode).data.id);
               treeNode = vm.treeControl.get_parent_branch(treeNode);
           }
           return nodeIds;
       }

       function getChildNode(treeNode, id) {
           var node;
           for(var i = 0; i < treeNode.length; i++) {
               if(treeNode[i].data.id === id) {
                   node = treeNode[i];
               }
           }
           return node;
       }

        function editTopic(node, vm) {
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
                    if (error.status === 404) {
                        reloadTopics(node, vm);
                    }
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