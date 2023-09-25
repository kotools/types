#!/bin/sh

# This scripts tags a stable release of Kotools Types.

TAG_NAME=$1
git checkout $TAG_NAME && \
git tag $TAG_NAME -fs -m "Stable release of Kotools Types $TAG_NAME." && \
git push origin $TAG_NAME -f
