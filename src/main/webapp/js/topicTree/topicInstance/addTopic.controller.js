(function () {
    'use strict';
    angular
        .module('eightyControllers')
        .controller('addTopicInstanceCtrl', addTopicInstanceCtrl);
    addTopicInstanceCtrl.$inject = ['$scope', 'modalData'];
    function addTopicInstanceCtrl($scope, modalData) {
        /*jshint validthis:true*/
        var vm = this;
        vm.shouldBeOpen = modalData.getShouldBeOpen();
        vm.topic = {};
        vm.createTopic = createTopic;
        vm.cancelTopicCreation = cancelTopicCreation;

        function createTopic() {
            if (vm.topic.title) {
                $scope.$close(vm.topic);
            }
        }

        function cancelTopicCreation() {
            modalData.setShouldBeOpen(false);
            $scope.$dismiss();
        }
    }
})();