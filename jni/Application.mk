#NDK_TOOLCHAIN_VERSION := 4.8
#APP_ABI := armeabi armeabi-v7a x86 mips
#  Enable C++11. However, pthread, rtti and exceptions aren’t enabled 
#"-DJNI -DANDROID -std=c++11"
APP_CFLAGS +="-DJNI -DANDROID"
APP_CPPFLAGS += -std=c++11
APP_CPPFLAGS += "-DJNI -DANDROID"
# Instruct to use the static GNU STL implementation
APP_STL := gnustl_static
LOCAL_C_INCLUDES += ${ANDROID_NDK}/sources/cxx-stl/gnu-libstdc++/4.8/include