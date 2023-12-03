#!/usr/bin/env python3

import argparse
import json
import os
import sys
import traceback
from collections import defaultdict

GROUND_TRUTH_FILE_NAME = 'ground_truth_report.json'


def ground_truth_path(generated_report, base_folder):
    assert 'language' in generated_report
    language = generated_report['language']

    if language == 'java':
        path = os.path.join(base_folder, language, 'bank', GROUND_TRUTH_FILE_NAME)
    elif language == 'go':
        path = os.path.join(base_folder, language, 'go-test-project', GROUND_TRUTH_FILE_NAME)

    else:
        assert 'not supported yet'

    assert os.path.exists(path)
    return path


def check_json(root_folder, report_filename):
    # Read and load the JSON from the file
    with open(report_filename, 'r') as file:
        generated_report = json.load(file)

    # Check if the status field is valued "Failed"
    if generated_report.get('status') != 'Completed':
        print("Status indicates failure")
        print(generated_report.get('failure_reason'))
        return 1

    with open(ground_truth_path(generated_report, base_folder=root_folder)) as report_json:
        ground_truth_report = json.load(report_json)

    # Define the required engines
    required_engines = ground_truth_report['engines']
    # Check if the engines array contains the required engines
    engines = generated_report.get('engines', [])
    for engine in required_engines:
        if engine not in engines:
            print(f"Missing engine: {engine}")
            return 1

    # Check for the existence of artifacts
    if not check_existence_of_artifacts(report_generated=generated_report):
        return 1

    if not compare_flows(report_generated=generated_report, report_expected=ground_truth_report):
        return 1

    if not compare_persistency(report_generated=generated_report, report_expected=ground_truth_report):
        return 1

    print("All checks passed")
    return 0


def compare_persistency(report_generated, report_expected):
    expected_persistency = dict()
    generated_persistency = dict()

    for artifact in report_expected['results']['artifacts']:
        if artifact['artifact_type'] == 'class_members' and \
                'persistent' in artifact['metadata']['values'] and \
                artifact['metadata']['values']['persistent']:
            expected_persistency[artifact['artifact_id']] = artifact

    for artifact in report_generated['results']['artifacts']:
        if artifact['artifact_type'] == 'class_members' and \
                'persistent' in artifact['metadata']['values'] and \
                artifact['metadata']['values']['persistent']:
            generated_persistency[artifact['artifact_id']] = artifact

    if generated_persistency.keys() != expected_persistency.keys():
        diff_in_generated = {k: v for k, v in generated_persistency.items() if k not in expected_persistency}
        diff_in_expected = {k: v for k, v in expected_persistency.items() if k not in generated_persistency}

        print(f'inconsistency between expected and generated artifacts: \n expected diff: {diff_in_expected} '
              f'\n generated: {diff_in_generated}')
        return False
    return True


def compare_flows(report_generated, report_expected):
    if 'Container Pii' in report_generated['results']['flows_result']['flows_artifacts']:
        report_generated['results']['flows_result']['flows_artifacts']['Encapsulated'] = \
            report_generated['results']['flows_result']['flows_artifacts'].pop('Container Pii')

    compare_test_passed = True
    try:
        for category in report_expected['results']['flows_result']['flows_artifacts']:
            for rule_type in report_expected['results']['flows_result']['flows_artifacts'][category]:
                flow_in_expected = report_expected['results']['flows_result']['flows_artifacts'][category][rule_type]
                if category not in report_generated['results']['flows_result']['flows_artifacts'] or \
                        rule_type not in report_generated['results']['flows_result']['flows_artifacts'][category]:
                    print(
                        f'inconsistency between expected and generated flows for category: '
                        f'{category}, rule_type: {rule_type} one of them not found in generated report')
                    compare_test_passed = False
                else:
                    flow_in_generated = report_generated['results']['flows_result']['flows_artifacts'][category][
                        rule_type]
                    expected_flows = {flow_unique_key(flow) for flow in flow_in_expected}
                    generated_flows = {flow_unique_key(flow) for flow in flow_in_generated}
                    if expected_flows != generated_flows:
                        compare_test_passed = False
                        print(
                            f'inconsistency between expected and generated flows category: {category}, rule_type: {rule_type}'
                            f' \n expected: {expected_flows} '
                            f'\n generated: {generated_flows}')
    except Exception as e:
        traceback.print_exc()
        return False

    return compare_test_passed


def flow_unique_key(flow):
    artifact_name = "_".join(flow['artifact_name'].split("_")[:-1])
    variable_name = ''
    if 'variable_name' in flow['metadata']['source']['metadata']:
        variable_name = flow['metadata']['source']['metadata']['variable_name']
    elif 'variable_name' in flow['metadata']['sink']['metadata']:
        variable_name = flow['metadata']['sink']['metadata']['variable_name']
    return f"{artifact_name}_{variable_name}"


def check_existence_of_artifacts(report_generated):
    expected_artifacts = ['class_members', 'container', 'functions', 'rest_api']
    d = defaultdict(list)
    for artifact in report_generated['results']['artifacts']:
        d[artifact['artifact_type']].append(artifact['artifact_name'])

    if len(d) != len(expected_artifacts):
        print(f'missing artifact types - expected:  {expected_artifacts} \t got: {d.keys()}')
        return False
    return True


def main():
    parser = argparse.ArgumentParser(
        description="check JSON report",
        usage="%(prog)s <root folder> <json file>"
    )

    parser.add_argument("root_folder", type=str, help="The root folder of the source code")

    # Define arguments
    parser.add_argument("json", type=str, help="The name of the json file to process.")

    # Parse arguments
    args = parser.parse_args()
    return check_json(root_folder=args.root_folder, report_filename=args.json)


if __name__ == "__main__":
    rc = main()
    sys.exit(rc)
