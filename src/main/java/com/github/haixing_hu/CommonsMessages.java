/******************************************************************************
 *
 * Copyright (c) 2013  Haixing Hu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package com.github.haixing_hu;


/**
 * This interface defines the common message constants.
 *
 * @author Haixing Hu
 */
public interface CommonsMessages {

  public static final String PATH_MUST_BE_ABSOLUTE             =
    "The path must be absolute. ";

  public static final String UNSUPPORTED_OPEN_OPTION            =
    "The specified open option is not supported by this function: ";

  public static final String READER_OPENED                      =
    "The reader for the file has already been opened. ";

  public static final String READER_NOT_OPENED                  =
    "The reader for the file is not opened. ";

  public static final String WRITER_OPENED                      =
    "The writer for the sequence file has been opened. ";

  public static final String WRITER_NOT_OPENED                  =
    "The writer for the sequence file is not opened. ";

  public static final String FILE_OPENED                        =
    "The file has already been opened. ";

  public static final String FILE_NOT_FOUND                     =
    "Can't find the file. ";

  public static final String DIRECTORY_NOT_FOUND                =
    "Can't find the directory. ";

  public static final String FILE_NOT_OPENED                    =
    "The file isn't opened. ";

  public static final String FILE_NOT_EXIST                     =
    "The file doesn't exist. ";

  public static final String FILE_ALREADY_EXIST                 =
    "The file already exists. ";

  public static final String DIRECTORY_ALREADY_EXIST            =
    "The directory already exists. ";

  public static final String FILE_IS_DIRECTORY                  =
    "The file exists but is a directory. ";

  public static final String FILE_IS_NOT_DIRECTORY              =
    "The file exists but is not a directory. ";

  public static final String FILE_IS_NOT_NORMAL                 =
    "The path exsits but is not a normal file.";

  public static final String FILE_CANT_READ                     =
    "The file can't be read. ";

  public static final String FILE_CANT_WRITE                    =
    "The file can't be written to. ";

  public static final String FILE_CANT_EXECUTE                  =
    "The file can't be executed. ";

  public static final String FILE_CANT_CREATE                   =
    "The file can't be created. ";

  public static final String FILE_CANT_DELETE                   =
    "The file can't be deleted. ";

  public static final String FILE_CANT_MOVE                     =
    "The file can't be moved. ";

  public static final String FILE_CANT_COPY                     =
    "The file can't be copied. ";

  public static final String FILE_WAS_LOCKED                    =
    "The file was locked. ";

  public static final String SET_FILE_MODIFY_TIME_FAILED        =
    "Setting the file last modify time failed. ";

  public static final String COPY_FILE_FAILED                   =
    "Copying file failed. ";

  public static final String INVALID_FILENAME                   =
    "Invalid file name. ";

  public static final String INVALID_DIRECTORY_NAME             =
    "Invalid directory name. ";

  public static final String DIRECTORY_NOT_EXIST                =
    "The directory doesn't exist. ";

  public static final String DIRECTORY_CANT_READ                =
    "The directory can't be read. ";

  public static final String DIRECTORY_CANT_WRITE               =
    "The directory can't be written to. ";

  public static final String DIRECTORY_CANT_LIST                =
    "Can't list the directory. ";

  public static final String DIRECTORY_CANT_CREATE              =
    "The directory can't be created. ";

  public static final String DIRECTORY_CANT_DELETE              =
    "The directory can't be deleted. ";

  public static final String DIRECTORY_CANT_MOVE                =
    "The directory can't be moved. ";

  public static final String DIRECTORY_CANT_COPY                =
    "The directory can't be copied. ";

  public static final String DIRECTORY_WAS_LOCKED               =
    "The directory was locked. ";

  public static final String DIRECTORY_CANT_COMPARE             =
    "Can't compare directories. ";

  public static final String DIRECTORY_CANT_CHECKSUM            =
    "Checksums can't be computed on directories. ";

  public static final String SET_DIRECTORY_MODIFY_TIME_FAILED   =
    "Setting the directory last modify time failed. ";

  public static final String SAME_SOURCE_DESTINATION            =
    "The source and destination are the same file or directory. ";

  public static final String LOADING_FILE                       =
    "Loading the file: {}";

  public static final String STORING_FILE                       =
    "Storing the file: {}";

  public static final String COPYING_FILES                      =
    "Copying files from {} to {} ...";

  public static final String COPYING_DIRECTORIES                =
    "Copying directories from {} to {} ...";

  public static final String SKIP_FILE                          =
    "Skip the file: {}";

  public static final String SKIP_DIRECTORY                     =
    "Skip the directory: {}";

  public static final String DUPLICATED_KEY                     =
    "The key was duplicated. ";

  public static final String EXCEPTION_THROW_DURING_CLOSING     =
    "An exception was thrown during closing the Closeable object. ";

  public static final String IO_EXCEPTION                       =
    "An I/O exception was thrown. ";

  public static final String INVALID_FORMAT                     =
    "Invalid format. ";

  public static final String UNMARKABLE_INPUT_STREAM            =
    "The input stream does not support mark. ";

  public static final String BUFFER_SIZE_MUST_POSITIVE          =
    "The buffer size must be greater than 0. ";

  public static final String UNEXPECTED_NULL_VALUE              =
    "Unexpected null value.";

  public static final String MALFORMED_VINT_ERROR               =
    "Malformed variant length encoded integer.";

  public static final String PARTIAL_CHAR_ERROR                 =
    "Malformed UTF-8 input: partial character at end. ";

  public static final String MALFORMED_UTF_ERROR                =
    "Malformed UTF-8 input around byte ";

  public static final String INVALID_ENUM_ORDINAL               =
    "Invalid enum ordinal: ";

  public static final String INVALID_ENUM_VALUES                =
    "Invalid enum constant array.";

  public static final String UNSUPPORTED_CLASS_ERROR            =
    "Unsupported class: ";

  public static final String UNEXPECTED_ERROR                   =
    "An unexpected error occurs. ";

  public static final String IGNORE_INTERRUPTED_EXCEPTION       =
    "Ignore the unwanted interruption. ";

  public static final String CREATE_INSTANCE_FAILED             =
    "Failed to create instance of ";

  public static final String MALFORMED_URL                      =
    "Malformed URL: ";

  public static final String MALFORMED_URI                      =
    "Malformed URI: ";

  public static final String FAILED_TO_EVALUATE_XPATH           =
    "Failed to evaluate the XPath expression: ";

  public static final String EMPTY_XML_NODE                     =
    "The XML node is empty.";

  public static final String NO_REQUIRED_XML_ATTRIBUTE          =
    "The XML has no required attribute.";

  public static final String INVALID_XML_STRUCTURE              =
    "The XML file has an invalid structure.";

  public static final String INVALID_ATTRIBUTE_VALUE            =
    "The XML attribute value is invalid.";

  public static final String XML_NODE_NAME                      =
    "XML node name: ";

  public static final String XML_ATTRIBUTE_NAME                 =
    "XML attribute name: ";

  public static final String XML_ATTRIBUTE_VALUE                =
    "XML attribute value: ";

  public static final String FAILED_CRATE_TEMP_AFTER_TRIES      =
    "Failed to create the temporary file after too many tries: ";

  public static final String FAILED_CRATE_TEMP_TRY_AGAIN        =
    "Failed to create the temporary file, try again: {}";

  public static final String FAILED_FOR_UNKNOWN_REASON          =
    "The operation failed for unknown reason.";

  public static final String UNSUPPORTED_CHECKSUM_ALGORITHM     =
    "Unsupported checksum algorithm. ";
}
