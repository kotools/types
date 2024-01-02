#!/bin/sh

# This script tags a stable release of Kotools Types.

TAG_NAME=$1
git tag "$TAG_NAME" -fs -m "Stable release of Kotools Types $TAG_NAME."
