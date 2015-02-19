'use strict';

describe('directives', function ($compile, $rootScope) {
    var formElement = angular.element('<form>' +
            '<input type="text" id = "firstInput" placeholder="Text input">' +
            '<input type="text" id = "secondInput" placeholder="Text input">' +
            '<button id="button" switch-off-branch>' +
            '</form>');

    var rootScope;

    beforeEach(module('eightyDirectives'));

    beforeEach(inject(function($compile, $rootScope) {
        rootScope = $rootScope;
        spyOn(rootScope, '$broadcast');
        $compile(formElement)($rootScope);
        rootScope.$digest();
    }));

    it('switch off branch when click', function() {
        formElement.find('#button').click();
        expect(rootScope.$broadcast).toHaveBeenCalledWith('switchOffBranch');
    });

});