import copy
import json
import os
from .test_json_results import compare_persistency, compare_flows, check_json, GROUND_TRUTH_FILE_NAME

LANG = 'java'
PROJECT = 'bank'
FILE_NAME = GROUND_TRUTH_FILE_NAME


def test_compare_persistency_func():
    with open(os.path.join(LANG, PROJECT, FILE_NAME), 'r') as file:
        report1 = json.load(file)

    report2 = report1
    assert compare_persistency(report1, report2), 'identical reports, expecting True'

    report2 = copy.deepcopy(report1)  # deep copy
    artifacts = report2['results']['artifacts']
    report2['results']['artifacts'] = [artifact for artifact in artifacts
                                       if artifact[
                                           'artifact_name'] != 'city@rib.entity.Address.city#src/rib/entity/Address.java' and
                                       artifact['artifact_name'] !=
                                       'phoneBankNumber@rib.entity.BankAgency.phoneBankNumber#src/rib/entity/BankAgency.java']

    assert not compare_persistency(report_expected=report1,
                                   report_generated=report2), 'As we removed artifacts expecting False'


def test_compare_flows_func():
    with open(os.path.join(LANG, PROJECT, FILE_NAME), 'r') as file:
        report1 = json.load(file)

    report2 = report1
    assert compare_flows(report_expected=report1, report_generated=report2), 'identical reports, expecting True'
    report2 = copy.deepcopy(report1)  # deep copy
    assert len(
        report2['results']['flows_result']['flows_artifacts']['Email']['sensitive-info-to-log']) > 0
    report2['results']['flows_result']['flows_artifacts']['Email']['sensitive-info-to-log'] = []
    assert not compare_flows(report_expected=report1,
                             report_generated=report2), 'As we removed two flows expecting False'
    report2 = copy.deepcopy(report1)  # deep copy
    assert len(
        report2['results']['flows_result']['flows_artifacts']['Phone']['sensitive-info-to-external-functions']) > 0
    report2['results']['flows_result']['flows_artifacts']['Phone']['sensitive-info-to-external-functions'].pop()
    assert not compare_flows(report_expected=report1,
                             report_generated=report2), 'As we removed two flows expecting False'


# In this test we make sure the Container PII key is replaced with Encapsulated (due to a bug in the offline scanner
# that keeps on using this key)
def test_compare_flows_func_with_container_pii():
    with open(os.path.join(LANG, PROJECT, FILE_NAME), 'r') as file:
        report1 = json.load(file)
    with open(os.path.join("test", "report_with_container_pii_for_testing_only.json"), 'r') as file:
        report2 = json.load(file)
    assert compare_flows(report_expected=report1, report_generated=report2), 'identical reports, expecting True'


def test_check_json():
    assert check_json(report_filename=os.path.join(LANG, PROJECT, FILE_NAME), root_folder=os.getcwd()) == 0, \
        'identical reports, expecting 0'
