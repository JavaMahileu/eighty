(function () {
    'use strict';
    /*exported isPrintLog */
    angular
        .module('eightyControllers')
        .controller('treeNavigationCtrl', treeNavigationCtrl);
    treeNavigationCtrl.$inject = ['$state', 'crudFactory', 'treeNavigationFactory', 'modalData'];
    function treeNavigationCtrl($state, crudFactory, treeNavigationFactory, modalData) {
        /*jshint validthis:true*/
        var vm = this;
        vm.treeControl = {};
        vm.treedata = [];
        vm.onTopicClick = onTopicClick;
        vm.onFolderHeadClick = onFolderHeadClick;
        vm.editTopic = editTopic;
        vm.addTopic = addTopic;
        vm.deleteTopic = deleteTopic;
        vm.setEditable = setEditable;
        vm.getEditable = getEditable;
        var editable = false;

        activate();

        function activate() {
            crudFactory.topic().get({id: ''}).$promise.then(function(topic) {
               vm.treedata = treeNavigationFactory.getTreeTopics(topic);
            }, function(error) {
                printLog(error);
            });
        }

        function onTopicClick(node) {
            $state.go('topics', {id: node.data.id});
        }

        function onFolderHeadClick(treeNode) {
            treeNode.data = treeNode.data || {id: ''};
            crudFactory.topic().get({id: treeNode.data.id}).$promise.then(function(topic) {
                treeNode.topics = treeNavigationFactory.getTreeTopics(topic);
                vm.treeControl.expand_branch(treeNode);
            }, function(error) {
                if (error.status === 404) {
                    treeNavigationFactory.reloadTopics(treeNode, vm);
                }
            });
        }

        function editTopic(node) {
            modalData.setShouldBeOpen(true);
            treeNavigationFactory.editTopic(node, vm);
        }

        function addTopic(node) {
            modalData.setShouldBeOpen(true);
            treeNavigationFactory.addTopic(vm, node);
        }

        function deleteTopic(node) {
            treeNavigationFactory.deleteTopic(node, vm);
        }

        function setEditable() {
            editable = !editable;
        }

        function getEditable() {
            return editable;
        }
    }
})();