################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
..\DomoBoard\ModbusDomoboard.cpp \
..\DomoBoard\domoBoard.cpp 

LINK_OBJ += \
.\DomoBoard\ModbusDomoboard.cpp.o \
.\DomoBoard\domoBoard.cpp.o 

CPP_DEPS += \
.\DomoBoard\ModbusDomoboard.cpp.d \
.\DomoBoard\domoBoard.cpp.d 


# Each subdirectory must supply rules for building sources it contributes
DomoBoard\ModbusDomoboard.cpp.o: ..\DomoBoard\ModbusDomoboard.cpp
	@echo 'Building file: $<'
	@echo 'Starting C++ compile'
	"C:\Users\Lauti\Sloeber\arduinoPlugin\packages\arduino\tools\avr-gcc\7.3.0-atmel3.6.1-arduino7/bin/avr-g++" -c -g -Os -w -std=gnu++11 -fpermissive -fno-exceptions -ffunction-sections -fdata-sections -fno-threadsafe-statics -Wno-error=narrowing -MMD -flto -mmcu=atmega328p -DF_CPU=16000000L -DARDUINO=10812 -DARDUINO_AVR_UNO -DARDUINO_ARCH_AVR   -I"C:\Users\Lauti\Desktop\1uni\3\Domotica\P9\P08_SensorAnalogico" -I"C:\Users\Lauti\Desktop\1uni\3\Domotica\P9\P08_SensorAnalogico\DomoBoard" -I"C:\Users\Lauti\Desktop\1uni\3\Domotica\P9\P08_SensorAnalogico\ModbusSlave" -I"C:\Users\Lauti\Sloeber\arduinoPlugin\packages\arduino\hardware\avr\1.8.6\variants\standard" -I"C:\Users\Lauti\Sloeber\arduinoPlugin\packages\arduino\hardware\avr\1.8.6\cores\arduino" -I"C:\Users\Lauti\Sloeber\arduinoPlugin\packages\arduino\hardware\avr\1.8.6\libraries\EEPROM\src" -I"C:\Users\Lauti\Sloeber\arduinoPlugin\packages\arduino\hardware\avr\1.8.6\variants\standard" -I"C:\Users\Lauti\Sloeber\arduinoPlugin\packages\arduino\hardware\avr\1.8.6\cores\arduino" -MMD -MP -MF"$(@:%.o=%.d)" -MT"$@" -D__IN_ECLIPSE__=1 "$<" -o "$@"
	@echo 'Finished building: $<'
	@echo ' '

DomoBoard\domoBoard.cpp.o: ..\DomoBoard\domoBoard.cpp
	@echo 'Building file: $<'
	@echo 'Starting C++ compile'
	"C:\Users\Lauti\Sloeber\arduinoPlugin\packages\arduino\tools\avr-gcc\7.3.0-atmel3.6.1-arduino7/bin/avr-g++" -c -g -Os -w -std=gnu++11 -fpermissive -fno-exceptions -ffunction-sections -fdata-sections -fno-threadsafe-statics -Wno-error=narrowing -MMD -flto -mmcu=atmega328p -DF_CPU=16000000L -DARDUINO=10812 -DARDUINO_AVR_UNO -DARDUINO_ARCH_AVR   -I"C:\Users\Lauti\Desktop\1uni\3\Domotica\P9\P08_SensorAnalogico" -I"C:\Users\Lauti\Desktop\1uni\3\Domotica\P9\P08_SensorAnalogico\DomoBoard" -I"C:\Users\Lauti\Desktop\1uni\3\Domotica\P9\P08_SensorAnalogico\ModbusSlave" -I"C:\Users\Lauti\Sloeber\arduinoPlugin\packages\arduino\hardware\avr\1.8.6\variants\standard" -I"C:\Users\Lauti\Sloeber\arduinoPlugin\packages\arduino\hardware\avr\1.8.6\cores\arduino" -I"C:\Users\Lauti\Sloeber\arduinoPlugin\packages\arduino\hardware\avr\1.8.6\libraries\EEPROM\src" -I"C:\Users\Lauti\Sloeber\arduinoPlugin\packages\arduino\hardware\avr\1.8.6\variants\standard" -I"C:\Users\Lauti\Sloeber\arduinoPlugin\packages\arduino\hardware\avr\1.8.6\cores\arduino" -MMD -MP -MF"$(@:%.o=%.d)" -MT"$@" -D__IN_ECLIPSE__=1 "$<" -o "$@"
	@echo 'Finished building: $<'
	@echo ' '


