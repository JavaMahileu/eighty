(function () {
    'use strict';
    angular
        .module('eightyControllers')
        .controller('deleteTopicInstanceCtrl', deleteTopicInstanceCtrl);
    deleteTopicInstanceCtrl.$inject = ['$scope'];
    function deleteTopicInstanceCtrl($scope) {
        /*jshint validthis:true*/
        var vm = this;
        vm.topic = {};
        vm.deleteTopic = deleteTopic;
        vm.cancelDeleteTopic = cancelDeleteTopic;

        function deleteTopic() {
            $scope.$close(vm.topic);
        }

        function cancelDeleteTopic() {
            $scope.$dismiss();
        }
    }
})();