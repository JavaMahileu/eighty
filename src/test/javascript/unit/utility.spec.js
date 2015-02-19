'use strict';

describe('utility', function() {
    var set = [{id:1}, {id:2}, {id:3}, {id:4}];
    var ob = {id:2};
    var $modal;

    var fakeModal = {
        command: null,
        modal: function(command) {
            this.command = command;
        }
    };
    var fakeModalPromise = {
        then: function(callback) {
            callback(fakeModal);
        }
    };

    beforeEach(function() {
        $modal = jasmine.createSpy("$modal").andReturn(fakeModalPromise);
    });

    it('get index', function() {
        expect(getIndex(set, ob)).toBe(1);
    });

    it('contains in set', function() {
        expect(containsInSet(set, ob)).toBe(true);
    });

    it('replace dot', function() {
        expect(replaceDOT('asd.asd')).toBe('asd|asd');
        expect(replaceDOT('asdasd')).toBe('asdasd');
    });

    it('printLog called', function() {
        spyOn(window, 'printLog');
        spyOn(window.console, 'log');
        printLog('Bla-bla-bla');
        expect(printLog).toHaveBeenCalled();
    });

    it('errorAlert called', function() {
        spyOn(window, 'errorAlert');
        errorAlert('Bla-bla-bla', $modal);
        expect(errorAlert).toHaveBeenCalled();
    });
});