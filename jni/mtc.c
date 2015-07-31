#include <jni.h>
#include <android/log.h>
#include "mtc_impl.h"
#include "mtc/mtc.h"
/*
 * Class:     com_almas_mtc_MTC
 * Method:    nativeFontCreate
 * Signature: (III)J
 */
JNIEXPORT jint JNICALL Java_com_almas_mtc_MTC_nativeFontCreate
  (JNIEnv * env, jobject obj, jint size, jint fore, jint back)
{
	void * font = mtc_font_create(size, fore, back);
	return (jint)font;
}

/*
 * Class:     com_almas_mtc_MTC
 * Method:    nativeFontDestroy
 * Signature: (J)J
 */
JNIEXPORT void JNICALL Java_com_almas_mtc_MTC_nativeFontDestroy
  (JNIEnv *env, jobject obj , jint font)
{
	mtc_font_destroy((void*) font);
}

/*
 * Class:     com_almas_mtc_MTC
 * Method:    nativeFontLineHeight
 * Signature: (J)J
 */
JNIEXPORT jint JNICALL Java_com_almas_mtc_MTC_nativeFontLineHeight
  (JNIEnv *env, jobject thiz, jint font)
{
	return mtc_font_line_height((void*) font);
}
/*
 * Class:     com_almas_mtc_MTC
 * Method:    nativeLayoutCreate
 * Signature: (J)J
 */
JNIEXPORT jint JNICALL Java_com_almas_mtc_MTC_nativeLayoutCreate
  (JNIEnv * env, jobject thiz, jint font)
{
	return (jint)mtc_create_layout(env, (void*)font);
}

/*
 * Class:     com_almas_mtc_MTC
 * Method:    nativeLayoutSetText
 * Signature: (JLjava/lang/String;)J
 */
JNIEXPORT void JNICALL Java_com_almas_mtc_MTC_nativeLayoutSetText
  (JNIEnv * env, jobject thiz, jint layout, jstring srcText)
{
	jboolean iscopy;
	const jchar *text = (*env)->GetStringChars(env, srcText, &iscopy);
	int num_chars = (*env)->GetStringLength(env, srcText);
	mtc_set_text((void*)layout, text , num_chars);
	(*env)->ReleaseStringChars(env, srcText, text);

}

/*
 * Class:     com_almas_mtc_MTC
 * Method:    nativeLayoutBreakLine
 * Signature: (JI)J
 */
JNIEXPORT jint JNICALL Java_com_almas_mtc_MTC_nativeLayoutBreakLine
  (JNIEnv *env, jobject thiz, jint layout, jint maxHeight)
{
	return mtc_break_line((void*)layout, maxHeight);
}

/*
 * Class:     com_almas_mtc_MTC
 * Method:    nativeLayoutDrawToBitmap
 * Signature: (IIIII)Z
 */
JNIEXPORT jboolean JNICALL Java_com_almas_mtc_MTC_nativeLayoutDrawToBitmap
  (JNIEnv *env, jobject thiz, jint layout, jint width, jint height, jint x, jint y)
{
	mtcLayoutDrawBitmap(env, thiz, (void*)layout, width, height, x, y, 0);
	return 1;
}

/*
 * Class:     com_almas_mtc_MTC
 * Method:    nativeLayoutGetCharPosition
 * Signature: (JII)J
 */
JNIEXPORT jint JNICALL Java_com_almas_mtc_MTC_nativeLayoutGetCharPosition
  (JNIEnv *env, jobject obj, jint layout, jint x, jint y)
{
	unsigned char trailling;
	int pos = mtc_get_char_position((void*)layout, x, y ,&trailling);

	__android_log_print(2, "mtc", "nativeLayoutGetCharPosition1 p=%d,t=%d", pos, (int)trailling);

	if(trailling)
		pos |= 0x80000000;
	__android_log_print(2, "mtc", "nativeLayoutGetCharPosition2 p=%d,t=%d,siseof(int)=%d", pos, (int)trailling, sizeof(int));
	return pos;
}

/*
 * Class:     com_almas_mtc_MTC
 * Method:    nativeLayoutGetCharLocation
 * Signature: (JIZ)J
 */
JNIEXPORT jlong JNICALL Java_com_almas_mtc_MTC_nativeLayoutGetCharLocation
  (JNIEnv *env, jobject obj, jint layout, jint pos, jboolean trailing)
{
	int x,y;
	mtc_get_char_location((void*)layout, pos, (unsigned char)trailing, (int*)&x, (int*)&y);

	jlong xl= x;
	jlong ret = (jlong)((xl << 32) | (jlong)y);
	__android_log_print(2, "MTC", "Java_com_almas_mtc_MTC_nativeLayoutGetCharLocation (xl << 32)=%ld", xl << 32);

	int x1,y1;
	x1 = ((int)(ret >> 32)) & 0x0FFFFFFFFL;
	y1 = ret & 0x0FFFFFFFFL;
	__android_log_print(2, "MTC", "Java_com_almas_mtc_MTC_nativeLayoutGetCharLocation x1=%d,y1=%d", x1, y1);


	return ret;
}

/*
 * Class:     com_almas_mtc_MTC
 * Method:    nativeLayoutDestroy
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_almas_mtc_MTC_nativeLayoutGetCharLocation2
  (JNIEnv *env, jobject obj, jint layout)
{
	mtc_destroy_layout((void*)layout);
}
JNIEXPORT jboolean JNICALL Java_com_almas_mtc_MTC_nativeDrawToBitmap
(JNIEnv * env, jobject thiz, jint textSize, jint width, jint height, jstring srcText, jobject lock)
{
	__android_log_print(2, "Java_com_almas_mtc_MTC_nativeDrawToBitmap", "entered");
	return mtcDrawToBitmap(env, thiz, textSize, width, height, srcText, lock);
}

