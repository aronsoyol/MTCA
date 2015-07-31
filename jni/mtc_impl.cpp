/*
 * mtc.cpp
 *
 *  Created on: 2015/07/28
 *      Author: aron
 */
#include <string.h>
#include <math.h>
#include <jni.h>
#include <android/log.h>
#include "mtc/util.h"
#include "mtc/ParaLayout.h"
#include "mtc_impl.h"
#include "mtc/mtc.h"
void mtcLayoutDrawBitmap
(JNIEnv * env, jobject thiz, void* layout, jint width, jint height, jint x, jint y, jobject lock)
{
	__android_log_print(2, "mtc", "mtcLayoutDrawBitmap entered width - %d, height - %d", width, height);
	int buff_size = width * height;
	std::vector<unsigned int> bitmap_buffer;
	MTC::LayoutEngine::ParaLayout* pLayout = (MTC::LayoutEngine::ParaLayout *)layout;

	bitmap_buffer.assign(buff_size, pLayout->back_color());

	mtc_draw(layout, (unsigned int*)&bitmap_buffer[0], width, height, x, y);

	jintArray intArray = (jintArray)env->NewIntArray( buff_size);
	jint *arrayBody = env->GetIntArrayElements( intArray, 0);
	for(int i = 0; i < buff_size; i++)
	{
		int col = arrayBody[i] & 0xffffff;
		if(col != 0 && col != 0xFFFFFF)
		{
			__android_log_print(2, "mtcLayoutDrawBitmap", "col = %d", col);
		}
		arrayBody[i] = bitmap_buffer[i];
	}

//	jclass jclass ;
//	jmethodID mid;
	env->PushLocalFrame(256);
	jclass cls = env->GetObjectClass( thiz);
	jmethodID mid = env->GetMethodID( cls, "javaDrawToBitmap", "([III)V");
	if (mid == 0) {
		__android_log_print(2, "mtcLayoutDrawBitmap", "%s", "method drawBitmap - failed");
		return ;
	}
	else
	{
		__android_log_print(2, "mtcLayoutDrawBitmap", "%s", "method drawBitmap - success");
	}


	env->ExceptionClear();
	__android_log_print(2, "mtc", "mtcLayoutDrawBitmap - [%s]", "env->ExceptionClear()");
//	env->MonitorEnter( lock);
	__android_log_print(2, "mtc", "mtcLayoutDrawBitmap - [%s]", "env->MonitorEnter( lock)");
	env->CallVoidMethod(thiz, mid, intArray, width, height);
	__android_log_print(2, "mtc", "mtcLayoutDrawBitmap - env->CallVoidMethod(thiz, mid, intArray, width(%d), height(%d))", width, height);
	env->ReleaseIntArrayElements( intArray, arrayBody, 0);
	__android_log_print(2, "mtc", "mtcLayoutDrawBitmap - [%s]", "env->ReleaseIntArrayElements( intArray, arrayBody, 0)");
	env->DeleteLocalRef(intArray);
	__android_log_print(2, "mtc", "mtcLayoutDrawBitmap - [%s]", "env->DeleteLocalRef(intArray)");
//	env->MonitorExit(lock);
	__android_log_print(2, "mtc", "mtcLayoutDrawBitmap - [%s]", "env->MonitorExit(lock)");

	env->DeleteLocalRef( cls);
	env->PopLocalFrame( NULL);

	if (env->ExceptionOccurred()) {
		__android_log_print(2, "drawIndicText:draw_bitmap", "%s\n",
				"error occurred copying array back");
		env->ExceptionDescribe();
		env->ExceptionClear();
	}
}
jboolean mtcDrawToBitmap
(JNIEnv * env, jobject thiz, jint textSize, jint width, jint height, jstring srcText, jobject lock)
{
	__android_log_print(2, "mtc", "drawToBitmap entered width=%d, height=%d", width, height);

	int buff_size = width * height;
	MTC::Util::FontOption fo(textSize, 0x00000000, 0x00FFFFFF);
	MTC::LayoutEngine::ParaLayout layout(env, &fo);
	std::vector<unsigned int> bitmap_buffer;
	bitmap_buffer.assign(buff_size, fo.back);

	__android_log_print(2, "mtc", "drawToBitmap step1");

	env->PushLocalFrame(256);

	jboolean iscopy;
	const jchar *text = env->GetStringChars( srcText, &iscopy);
	int num_chars = env->GetStringLength( srcText);

	__android_log_print(2, "mtc", "drawToBitmap text length - %d", num_chars);

	std::u16string u16text = std::u16string((const char16_t*)text, (const char16_t*)text + num_chars);


	__android_log_print(2, "mtc", "drawToBitmap step2");
	layout.set_text(u16text);
	layout.break_line(height);
	layout.draw(&bitmap_buffer[0], width, height, 0, 0);

	env->ReleaseStringChars(srcText, text);

	jintArray intArray = (jintArray)env->NewIntArray( buff_size);
	jint *arrayBody = env->GetIntArrayElements( intArray, 0);
	for(int i = 0; i < buff_size; i++)
	{
		int col = arrayBody[i] & 0xffffff;
		if(col != 0 && col != 0xFFFFFF)
		{
			__android_log_print(2, "mtc", "drawToBitmap col = %d", col);
		}
		arrayBody[i] = bitmap_buffer[i];
	}

//	jclass jclass ;
//	jmethodID mid;

	jclass cls = env->GetObjectClass( thiz);
	jmethodID mid = env->GetMethodID( cls, "javaDrawToBitmap", "([III)V");
	if (mid == 0) {
		__android_log_print(2, "drawToBitmap", "%s", "method drawBitmap - failed");
		return 0;
	}
	else
	{
		 __android_log_print(2, "drawToBitmap", "%s", "method drawBitmap - success");
	}


	env->ExceptionClear();
	__android_log_print(2, "mtc", "drawToBitmap - [%s]", "env->ExceptionClear()");
//	env->MonitorEnter( lock);
	__android_log_print(2, "mtc", "drawToBitmap - [%s]", "env->MonitorEnter( lock)");
	env->CallVoidMethod(thiz, mid, intArray, width, height);
	__android_log_print(2, "mtc", "drawToBitmap - env->CallVoidMethod(thiz, mid, intArray, width(%d), height(%d))", width, height);
	env->ReleaseIntArrayElements( intArray, arrayBody, 0);
	__android_log_print(2, "mtc", "drawToBitmap - [%s]", "env->ReleaseIntArrayElements( intArray, arrayBody, 0)");
	env->DeleteLocalRef(intArray);
	__android_log_print(2, "mtc", "drawToBitmap - [%s]", "env->DeleteLocalRef(intArray)");
//	env->MonitorExit(lock);
	__android_log_print(2, "mtc", "drawToBitmap - [%s]", "env->MonitorExit(lock)");

	env->DeleteLocalRef( cls);
	env->PopLocalFrame( NULL);

	if (env->ExceptionOccurred()) {
		__android_log_print(2, "drawIndicText:draw_bitmap", "%s\n",
				"error occurred copying array back");
		env->ExceptionDescribe();
		env->ExceptionClear();
	}


}


