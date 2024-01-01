#!/bin/sh

# Copyright 2023-2024 Loïc Lamarque.
# Use of this source code is governed by the MIT license.

# This script tags a stable release of Kotools Types.

TAG_NAME=$1
git tag "$TAG_NAME" -fs -m "Stable release of Kotools Types $TAG_NAME."
