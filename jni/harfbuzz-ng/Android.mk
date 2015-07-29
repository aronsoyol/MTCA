##
## Copyright (C) 2012 The Android Open Source Project
##
## Licensed under the Apache License, Version 2.0 (the "License");
## you may not use this file except in compliance with the License.
## You may obtain a copy of the License at
##
## http://www.apache.org/licenses/LICENSE-2.0
##
## Unless required by applicable law or agreed to in writing, software
## distributed under the License is distributed on an "AS IS" BASIS,
## WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
## See the License for the specific language governing permissions and
## limitations under the License.
##

LOCAL_PATH:= $(call my-dir)

HARFBUZZ_SRC_FILES = \
hb-blob.cc \
hb-buffer.cc \
hb-buffer-serialize.cc \
hb-common.cc \
hb-face.cc \
hb-fallback-shape.cc \
hb-font.cc \
hb-ft.cc \
hb-ot-font.cc \
hb-ot-layout.cc \
hb-ot-map.cc \
hb-ot-shape.cc \
hb-ot-shape-complex-arabic.cc \
hb-ot-shape-complex-default.cc \
hb-ot-shape-complex-hangul.cc \
hb-ot-shape-complex-hebrew.cc \
hb-ot-shape-complex-indic.cc \
hb-ot-shape-complex-indic-table.cc \
hb-ot-shape-complex-myanmar.cc \
hb-ot-shape-complex-sea.cc \
hb-ot-shape-complex-thai.cc \
hb-ot-shape-complex-tibetan.cc \
hb-ot-shape-fallback.cc \
hb-ot-shape-normalize.cc \
hb-ot-tag.cc \
hb-set.cc \
hb-shape.cc \
hb-shape-plan.cc \
hb-shaper.cc \
hb-unicode.cc \
hb-warning.cc \

#############################################################
# build the harfbuzz library
#

#include $(CLEAR_VARS)
#
#LOCAL_ARM_MODE := arm
#
#LOCAL_MODULE_TAGS := optional
#
#LOCAL_SRC_FILES:= \
# $(HARFBUZZ_SRC_FILES) \
# src/hb-icu.cc
#
#LOCAL_CPP_EXTENSION := .cc
#
#LOCAL_SHARED_LIBRARIES := \
# libcutils \
# libicuuc \
# libicui18n \
# libutils
#
#LOCAL_C_INCLUDES += \
# $(LOCAL_PATH)/src \
# external/icu4c/common
#
#LOCAL_CFLAGS += -DHB_NO_MT -DHAVE_OT -DHAVE_ICU
#
#LOCAL_LDLIBS += -lpthread
#
#LOCAL_MODULE:= libharfbuzz_ng
#
#include $(BUILD_SHARED_LIBRARY)

#############################################################
# build the harfbuzz static library
#
include $(CLEAR_VARS)

LOCAL_ARM_MODE := arm
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES:= \
$(HARFBUZZ_SRC_FILES) \
hb-ucdn.cc \
hb-ucdn/ucdn.c

LOCAL_CPP_EXTENSION := .cc
LOCAL_STATIC_LIBRARIES := ft2
LOCAL_C_INCLUDES := \
$(LOCAL_PATH)/src \
$(LOCAL_PATH)/src/hb-ucdn \
$(LOCAL_PATH)/../freetype/include
LOCAL_CFLAGS += -DHB_NO_MT -DHAVE_OT -DHAVE_UCDN -DHAVE_FREETYPE
#LOCAL_LDLIBS += -lpthread

LOCAL_MODULE:= harfbuzz

include $(BUILD_STATIC_LIBRARY)