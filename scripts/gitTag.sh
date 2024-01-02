#!/bin/sh

# This script tags a stable release of Kotools Types.

TAG_NAME=$1
git tag "$TAG_NAME" -s -m "Stable release of Kotools Types $TAG_NAME."
