(function(){
    'use strict';
    angular
        .module('eightyControllers')
        .controller('addQuestionInstanceCtrl', addQuestionInstance);

    addQuestionInstance.$inject = ['$scope', 'modalData', 'questionsFactory'];
    function addQuestionInstance ($scope,  modalData, questionsFactory) {
        /*jshint validthis:true*/
        var vm = this;

        vm.shouldBeOpen = modalData.getShouldBeOpen();
        vm.question = {};

        vm.createQuestion = function() {
            modalData.setShouldBeOpen(false);
            if (vm.question.question) {
                vm.question.like = 0;
                $scope.$close(vm.question);
            }
        };

        vm.cancelAddQuestion = function() {
            modalData.setShouldBeOpen(false);
            $scope.$dismiss();
        };

        vm.loadCustomers = function(query) {
            return questionsFactory.loadCustomersByName(query);
        };

        vm.loadTags = function(query) {
            return questionsFactory.loadTagsByName(query);
        };
    }
})();