package knu.cse.locker.manager.domain.account.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import knu.cse.locker.manager.domain.locker.entity.LockerLocation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/* 
 * ChangeEmailRequestDto.java
 *
 * @note 이메일 변경 API 요청에 사용되는 DTO
 *
 * @see knu.cse.locker.manager.domain.account.controller.AccountController#updateAccountEmail(ChangeEmailRequestDto, Authentication)
 *
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LockerDto {
    private LockerLocation lockerLocation;
    private String lockerNumber;
    private String lockerPassword;
    private Boolean lockerIsBroken;

    @Builder
    public LockerDto(LockerLocation lockerLocation, String lockerNumber, String lockerPassword, Boolean lockerIsBroken) {
        this.lockerLocation = lockerLocation;
        this.lockerNumber = lockerNumber;
        this.lockerPassword = lockerPassword;
        this.lockerIsBroken = lockerIsBroken;
    }
}
