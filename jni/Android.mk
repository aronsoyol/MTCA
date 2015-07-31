#Android.mk

#
# Copyright � 2012 Shiva Kumar H R
# 
# Permission is hereby granted, free of charge, to any person obtaining a 
# copy of this software and associated documentation files (the "Software"), 
# to deal in the Software without restriction, including without limitation 
# the rights to use, copy, modify, merge, publish, distribute, sublicense, 
# and/or sell copies of the Software, and to permit persons to whom the 
# Software is furnished to do so, subject to the following conditions:
# 
# The above copyright notice and this permission notice shall be included 
# in all copies or substantial portions of the Software.
# 
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS 
# OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
# THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
#

LOCAL_PATH := $(call my-dir)

MY_LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_ARM_MODE := arm

LOCAL_MODULE := mtc

LOCAL_C_INCLUDES := \
$(LOCAL_PATH)/freetype/include \
$(LOCAL_PATH)/harfbuzz-ng

LOCAL_SRC_FILES := \
	mtc.c \
	mtc_impl.cpp \
	mtc/Breaker.cpp \
	mtc/Canvas.cpp \
	mtc/hb_draw.cpp \
	mtc/mtc.cpp \
	mtc/ParaLayout.cpp \
	mtc/scrptitemizer.cpp \
	mtc/utils.cpp


LOCAL_STATIC_LIBRARIES := icu harfbuzz ft2

LOCAL_LDLIBS := -llog
LOCAL_CFLAGS += "-DJNI -DANDROID"
LOCAL_CPPFLAGS += "-DJNI -DANDROID"
LOCAL_CPPFLAGS := -std=c++11
LOCAL_C_INCLUDES += $(NDK_ROOT)/sources/cxx-stl/gnu-libstdc++/4.8/include
LOCAL_C_INCLUDES += $(NDK_ROOT)/sources/cxx-stl/gnu-libstdc++/4.8/libs/armeabi-v7a/include

include $(BUILD_SHARED_LIBRARY)

include $(MY_LOCAL_PATH)/freetype/Android.mk

include $(MY_LOCAL_PATH)/harfbuzz-ng/Android.mk