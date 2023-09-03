#!/usr/bin/env python3

import argparse
import json
import sys

def check_json(filename):
    # Read and load the JSON from the file
    with open(filename, 'r') as file:
        data = json.load(file)

    # Check if the status field is valued "Failed"
    if data.get('status') != 'Completed':
        print("Status indicates failure")
        print(data.get('failure_reason'))
        return 1

    # Define the required engines
    required_engines = ["functions", "rest_api", "sensitivity_scorer", "GitURLEngine"]

    # Check if the engines array contains the required engines
    engines = data.get('engines', [])
    for engine in required_engines:
        if engine not in engines:
            print(f"Missing engine: {engine}")
            return 1

    print("All checks passed")
    return 0


def main():
    parser = argparse.ArgumentParser(
        description="check JSON report",
        usage="%(prog)s <json file>"
    )

    # Define arguments
    parser.add_argument("json", type=str, help="The name of the json file to process.")

    # Parse arguments
    args = parser.parse_args()
    return check_json(args.json)


if __name__ == "__main__":
    rc=main()
    sys.exit(rc)

