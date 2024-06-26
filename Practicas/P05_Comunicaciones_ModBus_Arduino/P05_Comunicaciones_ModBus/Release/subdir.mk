################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
..\Gest_Modbus.cpp \
..\P05_Comunicaciones_ModBus.cpp \
..\utils_domoBoard.cpp 

LINK_OBJ += \
.\Gest_Modbus.cpp.o \
.\P05_Comunicaciones_ModBus.cpp.o \
.\utils_domoBoard.cpp.o 

CPP_DEPS += \
.\Gest_Modbus.cpp.d \
.\P05_Comunicaciones_ModBus.cpp.d \
.\utils_domoBoard.cpp.d 


# Each subdirectory must supply rules for building sources it contributes
Gest_Modbus.cpp.o: ..\Gest_Modbus.cpp
	@echo 'Building file: $<'
	@echo 'Starting C++ compile'
	"C:\Users\Lauti\Sloeber\arduinoPlugin\packages\arduino\tools\avr-gcc\7.3.0-atmel3.6.1-arduino7/bin/avr-g++" -c -g -Os -Wall -Wextra -std=gnu++11 -fpermissive -fno-exceptions -ffunction-sections -fdata-sections -fno-threadsafe-statics -Wno-error=narrowing -MMD -flto -mmcu=atmega328p -DF_CPU=16000000L -DARDUINO=10812 -DARDUINO_AVR_UNO -DARDUINO_ARCH_AVR   -I"C:\Users\Lauti\Sloeber\arduinoPlugin\packages\arduino\hardware\avr\1.8.6\cores\arduino" -I"C:\Users\Lauti\Desktop\1uni\3\Domotica\P05_Comunicaciones_ModBus_Arduino\P05_Comunicaciones_ModBus" -I"C:\Users\Lauti\Desktop\1uni\3\Domotica\P05_Comunicaciones_ModBus_Arduino\P05_Comunicaciones_ModBus\DomoBoard" -I"C:\Users\Lauti\Desktop\1uni\3\Domotica\P05_Comunicaciones_ModBus_Arduino\P05_Comunicaciones_ModBus\ModbusSlave" -I"C:\Users\Lauti\Sloeber\arduinoPlugin\packages\arduino\hardware\avr\1.8.6\variants\standard" -MMD -MP -MF"$(@:%.o=%.d)" -MT"$@" -D__IN_ECLIPSE__=1 "$<" -o "$@"
	@echo 'Finished building: $<'
	@echo ' '

P05_Comunicaciones_ModBus.cpp.o: ..\P05_Comunicaciones_ModBus.cpp
	@echo 'Building file: $<'
	@echo 'Starting C++ compile'
	"C:\Users\Lauti\Sloeber\arduinoPlugin\packages\arduino\tools\avr-gcc\7.3.0-atmel3.6.1-arduino7/bin/avr-g++" -c -g -Os -Wall -Wextra -std=gnu++11 -fpermissive -fno-exceptions -ffunction-sections -fdata-sections -fno-threadsafe-statics -Wno-error=narrowing -MMD -flto -mmcu=atmega328p -DF_CPU=16000000L -DARDUINO=10812 -DARDUINO_AVR_UNO -DARDUINO_ARCH_AVR   -I"C:\Users\Lauti\Sloeber\arduinoPlugin\packages\arduino\hardware\avr\1.8.6\cores\arduino" -I"C:\Users\Lauti\Desktop\1uni\3\Domotica\P05_Comunicaciones_ModBus_Arduino\P05_Comunicaciones_ModBus" -I"C:\Users\Lauti\Desktop\1uni\3\Domotica\P05_Comunicaciones_ModBus_Arduino\P05_Comunicaciones_ModBus\DomoBoard" -I"C:\Users\Lauti\Desktop\1uni\3\Domotica\P05_Comunicaciones_ModBus_Arduino\P05_Comunicaciones_ModBus\ModbusSlave" -I"C:\Users\Lauti\Sloeber\arduinoPlugin\packages\arduino\hardware\avr\1.8.6\variants\standard" -MMD -MP -MF"$(@:%.o=%.d)" -MT"$@" -D__IN_ECLIPSE__=1 "$<" -o "$@"
	@echo 'Finished building: $<'
	@echo ' '

utils_domoBoard.cpp.o: ..\utils_domoBoard.cpp
	@echo 'Building file: $<'
	@echo 'Starting C++ compile'
	"C:\Users\Lauti\Sloeber\arduinoPlugin\packages\arduino\tools\avr-gcc\7.3.0-atmel3.6.1-arduino7/bin/avr-g++" -c -g -Os -Wall -Wextra -std=gnu++11 -fpermissive -fno-exceptions -ffunction-sections -fdata-sections -fno-threadsafe-statics -Wno-error=narrowing -MMD -flto -mmcu=atmega328p -DF_CPU=16000000L -DARDUINO=10812 -DARDUINO_AVR_UNO -DARDUINO_ARCH_AVR   -I"C:\Users\Lauti\Sloeber\arduinoPlugin\packages\arduino\hardware\avr\1.8.6\cores\arduino" -I"C:\Users\Lauti\Desktop\1uni\3\Domotica\P05_Comunicaciones_ModBus_Arduino\P05_Comunicaciones_ModBus" -I"C:\Users\Lauti\Desktop\1uni\3\Domotica\P05_Comunicaciones_ModBus_Arduino\P05_Comunicaciones_ModBus\DomoBoard" -I"C:\Users\Lauti\Desktop\1uni\3\Domotica\P05_Comunicaciones_ModBus_Arduino\P05_Comunicaciones_ModBus\ModbusSlave" -I"C:\Users\Lauti\Sloeber\arduinoPlugin\packages\arduino\hardware\avr\1.8.6\variants\standard" -MMD -MP -MF"$(@:%.o=%.d)" -MT"$@" -D__IN_ECLIPSE__=1 "$<" -o "$@"
	@echo 'Finished building: $<'
	@echo ' '


