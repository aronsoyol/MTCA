/*
 * mtc_impl.h
 *
 *  Created on: 2015/07/28
 *      Author: aron
 */

#ifndef MTC_IMPL_H_
#define MTC_IMPL_H_

#ifdef __cplusplus
extern "C" {
#endif

jboolean mtcDrawToBitmap
(JNIEnv * env, jobject thiz, jint textSize, jint width, jint height, jstring srcText, jobject lock);

#ifdef __cplusplus
}
#endif

#endif /* MTC_IMPL_H_ */
