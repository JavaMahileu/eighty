(function () {
    'use strict';
    angular
        .module('eightyControllers')
        .controller('editTopicInstanceCtrl', editTopicInstanceCtrl);

    editTopicInstanceCtrl.$inject = ['$scope', 'modalData', 'node'];

    function editTopicInstanceCtrl($scope, modalData, node) {
        /*jshint validthis:true*/
        var vm = this;

        vm.shouldBeOpen = modalData.getShouldBeOpen();
        vm.topic = {title: node.title, id: node.data.id};
        vm.saveTopicChanges = saveTopicChanges;
        vm.cancelEditTopic = cancelEditTopic;

        function saveTopicChanges() {
            if (vm.topic.title) {
                $scope.$close(vm.topic);
            }
        }

        function cancelEditTopic() {
            modalData.setShouldBeOpen(false);
            $scope.$dismiss();
        }
    }
})();