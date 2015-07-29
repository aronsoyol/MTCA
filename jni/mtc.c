#include <jni.h>
#include <android/log.h>
#include "mtc_impl.h"
JNIEXPORT jboolean JNICALL Java_com_almas_mtc_MTC_nativeDrawToBitmap
(JNIEnv * env, jobject thiz, jint textSize, jint width, jint height, jstring srcText, jobject lock)
{
	__android_log_print(2, "Java_com_almas_mtc_MTC_nativeDrawToBitmap", "entered");
	return mtcDrawToBitmap(env, thiz, textSize, width, height, srcText, lock);
}
