export function getRoleName(role) {
    role = role.toString();
    switch (role) {
        case "ROLE_ADMIN": return "Admin";
        case "ROLE_CLIENT": return "Client";
        case "ROLE_OWNER": return "Owner";
        case "ROLE_BLOCKED": return "Blocked Owner";
        default: return role;
    }
}

export function isRole(user, role) {
    return user.roles.toString() == role;
}