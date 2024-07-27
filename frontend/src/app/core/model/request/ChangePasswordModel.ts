export interface ChangePasswordModel {
    old_password: string | null,
    new_password: string,
    confirm_password: string
}
